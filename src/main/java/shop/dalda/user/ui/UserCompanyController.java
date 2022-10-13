package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserAuthService;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserCompanyListResponse;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.user.ui.dto.UserCompanyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
                              UserCompanyRequest requestDto,
                              @AuthenticationPrincipal CustomOAuth2User currentUser) throws Exception {
        currentUser = userAuthService.getAuthenticationIsNull(request, currentUser);
        userService.saveOrUpdateCompany(currentUser, requestDto);
    }

    @Operation(summary = "업체 조회", description = "도메인 이름으로 업체정보를 조회하는 메서드")
    @GetMapping("/{companyDomain}")
    public ResponseEntity<UserCompanyResponse> getCompanyPage(@Parameter(name = "companyDomain") @PathVariable String companyDomain) {
        UserCompanyResponse response = userService.getCompanyPage(companyDomain);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "업체 리스트 조회", description = "최근 로그인 기준 업체 10개 조회")
    @GetMapping()
    public ResponseEntity<List<UserCompanyListResponse>> getCompanyList() {
        List<UserCompanyListResponse> resultList = userService.getCompanyList();
        return ResponseEntity.ok().body(resultList);
    }
}
