package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dalda.util.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "UserAuthController")
@RequiredArgsConstructor
@RequestMapping("/api/user-auth")
@RestController
public class UserAuthController {

    private final AuthService authService;

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급 하는 메서드")
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        String newAccessToken = authService.refreshToken(request, response, request.getHeader("Authorization"));
        return ResponseEntity.ok().body(newAccessToken);
    }
}
