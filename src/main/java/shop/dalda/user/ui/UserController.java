package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserAuthResponse;
import shop.dalda.user.ui.dto.UserUpdateRequest;
import shop.dalda.util.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "UserController")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Operation(summary = "유저정보 조회", description = "Request에 필요한 유저정보 반환")
    @GetMapping()
    public ResponseEntity<UserAuthResponse> getUserAuth(HttpServletRequest request, @AuthenticationPrincipal CustomOAuth2User currentUser) {
        if (currentUser == null) {
            currentUser = (CustomOAuth2User)authService.getAuthentication(request);
        }

        UserAuthResponse res = userService.getUserAuthById(currentUser.getId());
        return ResponseEntity.ok().body(res);
    }
    @Operation(summary = "유저정보 수정", description = "유저정보를 수정하는 메서드")
    @PatchMapping()
    public void updateUser(UserUpdateRequest requestDto,
                           @Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User currentUser) throws Exception {
        userService.updateUser(currentUser, requestDto);
    }


}
