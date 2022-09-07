package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserUpdateRequest;

@Tag(name = "UserController")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저정보 수정", description = "유저정보를 수정하는 메서드")
    @PatchMapping()
    public void updateUser(UserUpdateRequest requestDto,
                           @Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User currentUser) throws Exception {
        userService.updateUser(currentUser, requestDto);
    }


}
