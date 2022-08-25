package shop.dalda.user.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("관리자"),
    MEMBER("멤버"),
    GUEST("손님");

    private final String role;
}
