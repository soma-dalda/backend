package shop.dalda.security.auth.user;

import lombok.Builder;
import lombok.Getter;
import shop.dalda.user.domain.User;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private String oauthId;
    private String username;

    @Builder
    public OAuthAttributes(String oauthId, String username) {
        this.oauthId = oauthId;
        this.username = username;
    }

    public static OAuthAttributes of(String provider, String userNameAttributeName, Map<String, Object> attributes) {
        if (provider.equals("google"))
            return ofGoogle(attributes, createAuthId(attributes, provider, userNameAttributeName));
        else if (provider.equals("kakao"))
            return ofKakao(attributes, createAuthId(attributes, provider, userNameAttributeName));
        else if (provider.equals("naver"))
            return ofNaver(attributes, createAuthId(attributes, provider, userNameAttributeName));
        else throw new IllegalArgumentException("유효하지 않은 provider 타입 입니다.");
    }

    //구글
    private static OAuthAttributes ofGoogle(Map<String, Object> attributes, String authId) {
        return OAuthAttributes.builder()
                .oauthId(authId)
                .username((String) attributes.get("name"))
                .build();
    }

    //카카오
    private static OAuthAttributes ofKakao(Map<String, Object> attributes, String authId) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuthAttributes.builder()
                .oauthId(authId)
                .username((String) profile.get("nickname"))
                .build();
    }

    //네이버
    private static OAuthAttributes ofNaver(Map<String, Object> attributes, String authId) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .oauthId(authId)
                .username((String) response.get("name"))
                .build();
    }

    public static String createAuthId(Map<String, Object> attributes, String provider, String userNameAttributeName) {
        // naver만 규칙이 다름
        if (provider.equals("naver")) {
            Map<String, Object> tmpResponse = (Map<String, Object>) attributes.get("response");
            return provider + "_" + tmpResponse.get("id");
        } else return provider + "_" + attributes.get(userNameAttributeName);
    }

    public User toEntity() {
        return User.builder()
                .oauthId(this.oauthId)
                .username(this.username)
                .build();
    }
}
