package shop.dalda.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.security.jwt.TokenProvider;
import shop.dalda.util.CookieUtil;
import shop.dalda.util.service.RedisService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final RedisService redisService;
    private final TokenProvider tokenProvider;
    @Value("${app.oauth2.redirectUri}")
    private String REDIRECT_URI;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Optional<Cookie> accessTokenCookie = CookieUtil.getCookie(request, "accessToken");

        // 토큰 없으면 생략
        if (accessTokenCookie.isPresent()) {
            // redis에서 refreshToken 제거
            String accessToken = accessTokenCookie.get().getValue();
            CustomOAuth2User user = (CustomOAuth2User)tokenProvider.getAuthentication(accessToken).getPrincipal();
            redisService.deleteValues(Long.toString(user.getId()));

            // delete access, refresh token
            CookieUtil.deleteCookie(request, response, "accessToken");
        }

        CookieUtil.deleteCookie(request, response, "refreshToken");
        getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI);
    }
}
