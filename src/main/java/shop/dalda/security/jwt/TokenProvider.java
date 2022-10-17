package shop.dalda.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.util.CookieUtil;
import shop.dalda.util.service.RedisService;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private final RedisService redisService;

    @Value("${app.auth.tokenExpiration}")
    private long TOKEN_EXPIRATION;

    @Value("${app.auth.tokenSecret}")
    private String TOKEN_SECRET;

    public String createAccessToken(Authentication authentication, HttpServletResponse response) {
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
        // 유저 권한
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + TOKEN_EXPIRATION); // 30분

        //build accessToken
        return Jwts.builder()
                        .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                        .setSubject(Long.toString(user.getId()))
                        .claim("role", role)
                        .setIssuedAt(now)
                        .setExpiration(expiration)
                        .compact();
    }

    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

        Date now = new Date();
        Date refreshExpiration = new Date(now.getTime() + TOKEN_EXPIRATION * 48); // 1일


        //build refreshToken
        String refreshToken =
                Jwts.builder()
                        .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                        .setIssuedAt(now)
                        .setExpiration(refreshExpiration)
                        .compact();

        // Redis 저장소에 refreshToken 저장
        redisService.setValues(Long.toString(user.getId()), refreshToken, Duration.ofDays(1));

        // addCookie
        CookieUtil.addCookie(response, "refreshToken", refreshToken, (TOKEN_EXPIRATION * 48) / 1000);
    }

    //AccessToken 을 검사하고 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        CustomOAuth2User principal = new CustomOAuth2User(Long.valueOf(claims.getSubject()), "", "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // AccessToken 만료 시, 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Bearer 잘라내기
    public String parseBearerToken(String token) {
        return token.substring(7);
    }

    // Access Token 검증
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 토큰입니다.");
        } catch (IllegalStateException e) {
            throw new JwtException("잘못된 토큰입니다.");
        } catch (Exception ignored) {
        }
        return false;
    }
}
