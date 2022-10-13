package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dalda.user.application.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "UserAuthController")
@RequiredArgsConstructor
@RequestMapping("/api/user-auth")
@RestController
public class UserAuthController {

    private final UserAuthService userAuthService;

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급 하는 메서드")
    @PreAuthorize("hasRole('MEMBER') or hasRole('COMPANY')")
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        String newAccessToken = userAuthService.refreshToken(request, response);
        return ResponseEntity.ok().body(newAccessToken);
    }
}
