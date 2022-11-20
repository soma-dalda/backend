package shop.dalda.user.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_MEMBER("회원"),
    ROLE_COMPANY("업체");

    private final String role;
}
