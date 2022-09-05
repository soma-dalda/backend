package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.user.ui.dto.UserCompanyResponse;

@Tag(name = "UserCompanyController")
@RequiredArgsConstructor
@RequestMapping("/api/user-company")
@RestController
public class UserCompanyController {

    private final UserService userService;

    @Operation(summary = "업체 등록/수정", description = "유저가 업체를 등록/수정하는 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업체 등록/수정 성공")
    })
    @PatchMapping("")
    public void updateCompany(UserCompanyRequest requestDto,
                              @Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User currentUser) throws Exception {
        userService.saveOrUpdateCompany(currentUser, requestDto);
    }

    @Operation(summary = "업체 조회", description = "도메인 이름으로 업체정보를 조회하는 메서드")
    @GetMapping("/{companyDomain}")
    public ResponseEntity<UserCompanyResponse> company(@Parameter(name = "업체 도메인") @PathVariable String companyDomain) {
        UserCompanyResponse response = userService.getCompanyPage(companyDomain);
        return ResponseEntity.ok().body(response);
    }
}
