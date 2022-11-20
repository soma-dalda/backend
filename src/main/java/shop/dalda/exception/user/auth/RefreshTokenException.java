package shop.dalda.exception.user.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RefreshTokenException extends RuntimeException{
    private final String message;
}
