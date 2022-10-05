package shop.dalda.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.util.CookieUtil;
import shop.dalda.util.service.RedisService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Value("${app.oauth2.defaultUri}")
    private String DEFAULT_URI;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Optional<Cookie> refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");

        // delete cookie is not null
        if (refreshTokenCookie.isPresent()) {
            CookieUtil.deleteCookie(request, response, "refreshToken");
        }

        getRedirectStrategy().sendRedirect(request, response, DEFAULT_URI);
    }
}
