package shop.dalda.user.ui;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.application.UserService;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.user.ui.dto.UserCompanyResponse;
import shop.dalda.user.ui.dto.UserUpdateRequest;
import shop.dalda.util.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "UserController")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        String newAccessToken = authService.refreshToken(request, response, request.getHeader("accessToken"));
        return ResponseEntity.ok().body(newAccessToken);
    }

    @ApiOperation(value = "업체 등록/수정", notes = "유저가 업체를 등록/수정하는 메서드")
    @PutMapping("/user-company")
    public void updateCompany(UserCompanyRequest requestDto, @AuthenticationPrincipal CustomOAuth2User principal) throws Exception {
        System.out.println(principal);
        Long userId = principal.getId();
        userService.saveOrUpdateCompany(userId, requestDto);
    }

    @ApiOperation(value = "유저정보 수정", notes = "유저정보를 수정하는 메서드")
    @PutMapping("/user")
    public void updateUser(UserUpdateRequest requestDto, @AuthenticationPrincipal CustomOAuth2User principal) throws Exception {
        Long userId = principal.getId();
        userService.updateUser(userId, requestDto);
    }

    @ApiOperation(value = "업체 조회", notes = "도메인 이름으로 업체정보를 조회하는 메서")
    @GetMapping("/{companyDomain}")
    public ResponseEntity<UserCompanyResponse> company(@PathVariable String companyDomain) {
        UserCompanyResponse response = userService.getCompanyPage(companyDomain);
        return ResponseEntity.ok().body(response);
    }
}
