package shop.dalda.security.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import shop.dalda.security.jwt.TokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    private final CookieAuthorizationRequestRepository authorizationRequestRepository;
    private final TokenProvider tokenProvider;

    @Value("${app.oauth2.loginRedirectUri}")
    private String REDIRECT_URI;

    @Value("${app.oauth2.host}")
    private String HOST;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUri = determineTargetUrl(request, response, authentication);
        log.info("targetUri : " + targetUri);
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        // CookieAuthorizationRequestRepository 비우고
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        /**
         * redirect
         * // 쿠키에서 redirectUri 추출
         *         Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
         *                 .map(Cookie::getValue);
         *
         *         // host 확인
         *         if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
         *             throw new BadRequestException("unknown host");
         *         }
         *         String targetUri = redirectUri.orElse(DEFAULT_URI);
         */

        String targetUri = REDIRECT_URI;

        // Token 생성
        String accessToken = tokenProvider.createAccessToken(authentication, response);
        tokenProvider.createRefreshToken(authentication, response);

        return UriComponentsBuilder.fromUriString(targetUri)
                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);

        // URI 검증을 마쳤으므로 authorizationRequestRepository 에서 쿠키정보 삭제
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri.trim());

        // host 비교
        return HOST.equalsIgnoreCase(clientRedirectUri.getHost());
    }
}
