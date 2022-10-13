package shop.dalda.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.domain.Role;

import java.util.ArrayList;
import java.util.Collection;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(Role.ROLE_MEMBER::toString);

        CustomOAuth2User principal = CustomOAuth2User.builder()
                .id(annotation.id())
                .username("username")
                .password(annotation.password())
                .authorities(roles)
                .build();
        Authentication authenticationToken
                = new UsernamePasswordAuthenticationToken(principal,
                principal.getPassword(), principal.getAuthorities());

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
