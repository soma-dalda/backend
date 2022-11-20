package shop.dalda.security.jwt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);

        if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
            //Access Token 검사
            String parsedToken = tokenProvider.parseBearerToken(token);

            if (tokenProvider.validateToken(parsedToken)) {
                //Authentication 객체 생성
                Authentication authentication = tokenProvider.getAuthentication(parsedToken);
                // SecurityContextHolder의 SecurityContext에 Authentication 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(authentication.getName() + "의 인증정보 저장");
            } else {
                log.info("유효한 토큰이 아닙니다.");
            }
        }
        else {
            log.info("올바른 토큰이 아닙니다.");
        }
        filterChain.doFilter(request, response);
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
