package shop.dalda.user.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dalda.util.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api")
@RestController
public class UserController {

    private AuthService authService;

    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        String newAccessToken = authService.refreshToken(request, response, request.getHeader("accessToken"));
        return ResponseEntity.ok().body(newAccessToken);
    }
}
