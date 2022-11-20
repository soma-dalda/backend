package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserAuthService;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserAuthResponse;
import shop.dalda.user.ui.dto.UserProfileImageRequest;
import shop.dalda.user.ui.dto.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "UserController")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserAuthService userAuthService;

    @Operation(summary = "유저정보 조회", description = "유저정보 반환")
    @GetMapping()
    public ResponseEntity<UserAuthResponse> getUserAuth(HttpServletRequest request,
                                                        @AuthenticationPrincipal CustomOAuth2User currentUser) {
        currentUser = userAuthService.getAuthenticationIsNull(request, currentUser);

        UserAuthResponse res = userService.getUserAuthById(currentUser.getId());
        return ResponseEntity.ok().body(res);
    }
    @Operation(summary = "유저정보 수정", description = "유저정보를 수정하는 메서드 (이름, 전화번호)")
    @PreAuthorize("hasRole('MEMBER') or hasRole('COMPANY')")
    @PatchMapping()
    public void updateUser(HttpServletRequest request,
                           @Valid @RequestBody UserUpdateRequest requestDto,
                           @AuthenticationPrincipal CustomOAuth2User currentUser) {
        currentUser = userAuthService.getAuthenticationIsNull(request, currentUser);
        userService.updateUser(currentUser, requestDto);
    }

    @Operation(summary = "유저 프로필 이미지 변경", description = "유저의 프로필 이미지를 변경하는 메서드")
    @PreAuthorize("hasRole('MEMBER') or hasRole('COMPANY')")
    @PatchMapping("/profile")
    public void updateProfileImage(HttpServletRequest request,
                                   @RequestBody UserProfileImageRequest requestDto,
                                   @AuthenticationPrincipal CustomOAuth2User currentUser) {
        currentUser = userAuthService.getAuthenticationIsNull(request, currentUser);

        userService.updateUserProfileImage(currentUser, requestDto);
    }
}
