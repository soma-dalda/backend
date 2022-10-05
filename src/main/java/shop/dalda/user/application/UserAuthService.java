package shop.dalda.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.security.jwt.TokenProvider;
import shop.dalda.util.CookieUtil;
import shop.dalda.util.service.RedisService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    public CustomOAuth2User getAuthenticationIsNull(HttpServletRequest request, CustomOAuth2User user) {
        if (user == null) {
            String accessToken = request.getHeader("Authorization");
            user = (CustomOAuth2User)tokenProvider.getAuthentication(accessToken);
        }
        return user;
    }

    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // 리프레시 토큰 검증
        String oldAccessToken = request.getHeader("Authorization");
        String oldRefreshToken = CookieUtil.getCookie(request, "refreshToken")
                .map(Cookie::getValue).orElseThrow(() -> new RuntimeException("리프레시 토큰이 존재하지 않습니다.."));

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw new RuntimeException("올바르지 않은 리프레시 토큰입니다.");
        }

        // 기존 액세스 토큰으로 유저정보 받기
        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
        Long userId = user.getId();

        // Redis 에 저장된 refreshToken
        String savedRefreshToken = redisService.getValues(Long.toString(userId));

        // 요청 refresh token 과 비교
        if (!savedRefreshToken.equals(oldRefreshToken)) {
            throw new RuntimeException("일치하지 않는 리프레시 토큰입니다.");
        }

        // 토큰 재발급
        String accessToken = tokenProvider.createAccessToken(authentication, response);
        tokenProvider.createRefreshToken(authentication, response);
        return accessToken;
    }
}