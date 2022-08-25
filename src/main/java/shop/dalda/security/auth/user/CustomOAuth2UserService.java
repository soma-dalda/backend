package shop.dalda.security.auth.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import shop.dalda.user.application.UserService;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //null 체크
        Assert.notNull(userRequest, "null request");

        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();

        // OAuth 서버 고유 id값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //getRegistrationId (kakao, naver, google)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        //create User entity
        OAuthAttributes attribute = OAuthAttributes.of(provider,userNameAttributeName,attributes);
        User user = attribute.toEntity();

        //check oldUser
        Optional<User> oldUser = userRepository.findByOauthId(user.getOauthId());

        //saveOrUpdate create CustomOAuth2User
        CustomOAuth2User oAuth2User;
        if(oldUser.isPresent()) {
            userService.saveOrUpdate(oldUser.get());
            log.info("update oldUser");
            oAuth2User = CustomOAuth2User.create(oldUser.get());
        }
        else {
            userService.saveOrUpdate(user);
            log.info("save newUser");
            oAuth2User = CustomOAuth2User.create(user);
        }
        log.info(oAuth2User.toString());
        //return OAuth2User
        return oAuth2User;
    }
}
