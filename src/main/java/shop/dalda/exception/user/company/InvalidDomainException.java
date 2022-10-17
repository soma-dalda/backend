package shop.dalda.exception.user.company;

import lombok.Getter;

@Getter
public class InvalidDomainException extends RuntimeException{
    private final String message = "올바르지 않은 도메인 주소입니다.";
}
