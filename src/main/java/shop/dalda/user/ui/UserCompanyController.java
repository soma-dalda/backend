package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserAuthService;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserCompanyRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "UserCompanyController")
@RequiredArgsConstructor
@RequestMapping("/api/user-company")
@RestController
public class UserCompanyController {

    private final UserService userService;
    private final UserAuthService userAuthService;

    @Operation(summary = "업체 등록/수정", description = "유저가 업체를 등록/수정하는 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업체 등록/수정 성공")
    })
    @PreAuthorize("hasRole('MEMBER') or hasRole('COMPANY')")
    @PatchMapping()
    public void updateCompany(HttpServletRequest request,
                              @Valid @RequestBody UserCompanyRequest requestDto,
                              @AuthenticationPrincipal CustomOAuth2User currentUser) throws Exception {
        currentUser = userAuthService.getAuthenticationIsNull(request, currentUser);
        userService.saveOrUpdateCompany(currentUser, requestDto);
    }
}
