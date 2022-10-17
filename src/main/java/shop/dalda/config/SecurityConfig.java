package shop.dalda.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import shop.dalda.security.auth.CookieAuthorizationRequestRepository;
import shop.dalda.security.auth.LogoutSuccessHandler;
import shop.dalda.security.auth.OAuth2AuthenticationFailureHandler;
import shop.dalda.security.auth.OAuth2AuthenticationSuccessHandler;
import shop.dalda.security.auth.user.CustomOAuth2UserService;
import shop.dalda.security.jwt.JwtAccessDeniedHandler;
import shop.dalda.security.jwt.JwtAuthenticationEntryPoint;
import shop.dalda.security.jwt.JwtAuthenticationFilter;
import shop.dalda.security.jwt.JwtExceptionFilter;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    private static final String[] ignores = {
            "/favicon.ico",
            "/error",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //접근권한
        http.authorizeRequests()
                        .anyRequest().permitAll();

        http.formLogin().disable();

        //OAuth
        http.oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(cookieAuthorizationRequestRepository) // 인가요청 시작부터 콜백시점까지 OAuth2AuthorizationRequest 정보를 유지시켜줌
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        http.logout()
                .logoutSuccessHandler(logoutSuccessHandler);

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http.addFilterBefore(jwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Order(0)
    @Bean
    public SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http.requestMatchers(matchers -> matchers.antMatchers(ignores))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();
        return http.build();
    }
}
