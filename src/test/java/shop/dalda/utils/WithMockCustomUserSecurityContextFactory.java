package shop.dalda.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomOAuth2User> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomOAuth2User annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        Authentication authenticationToken
                = new UsernamePasswordAuthenticationToken(annotation.id(),
                "",
                Arrays.asList(new SimpleGrantedAuthority(annotation.authority())));

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
