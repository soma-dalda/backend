package shop.dalda.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigDev {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf().disable();
        //접근권한
        http.authorizeRequests().anyRequest().permitAll();

        http.httpBasic();

        return http.build();
    }

//    @Order(0)
//    @Bean
//    public SecurityFilterChain resources(HttpSecurity http) throws Exception {
//        http.requestMatchers(matchers -> matchers.antMatchers("/swagger-ui/**"))
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//                .requestCache().disable()
//                .securityContext().disable()
//                .sessionManagement().disable();
//        return http.build();
//    }
}
