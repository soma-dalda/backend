package shop.dalda.exception.user.company;

import lombok.Getter;

@Getter
public class DomainNotFoundException extends RuntimeException{
    private final String message = "존재하지 않는 도메인 주소입니다.";
}
