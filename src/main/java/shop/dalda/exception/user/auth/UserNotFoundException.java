package shop.dalda.exception.user.auth;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final String ERROR_CODE = "U001";
    private static final String MESSAGE = "사용자를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}