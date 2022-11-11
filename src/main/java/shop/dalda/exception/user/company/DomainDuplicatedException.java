package shop.dalda.exception.user.company;

import lombok.Getter;

@Getter
public class DomainDuplicatedException extends RuntimeException{
    private final String message = "이미 사용중인 도메인명 입니다.";
}
