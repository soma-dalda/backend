package shop.dalda.exception;

import org.springframework.http.HttpStatus;

public class SQLInjectionException extends RuntimeException {
    
    private final String ERROR_CODE = "T001";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "SQL Injection을 야기할 수 있는 입력값입니다.";

    public SQLInjectionException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}