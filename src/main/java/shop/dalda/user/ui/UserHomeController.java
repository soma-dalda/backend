package shop.dalda.user.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserCompanyListResponse;
import shop.dalda.user.ui.dto.UserCompanyResponse;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/home/user")
@RestController
public class UserHomeController {

    private final UserService userService;

    @Operation(summary = "업체 조회", description = "도메인 이름으로 업체정보를 조회하는 메서드")
    @GetMapping("/company/{companyDomain}")
    public ResponseEntity<UserCompanyResponse> getCompanyPage(@Parameter(name = "companyDomain") @PathVariable String companyDomain) {
        UserCompanyResponse response = userService.getCompanyPage(companyDomain);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "업체 리스트 조회", description = "최근 로그인 기준 업체 10개 조회")
    @GetMapping("/company/list")
    public ResponseEntity<List<UserCompanyListResponse>> getCompanyList() {
        List<UserCompanyListResponse> resultList = userService.getCompanyList();
        return ResponseEntity.ok().body(resultList);
    }
}
