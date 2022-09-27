package shop.dalda.user.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("관리자"),
    MEMBER("회원"),
    COMPANY("업체");

    private final String role;
}
