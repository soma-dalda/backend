package shop.dalda.exception;

import org.springframework.http.HttpStatus;

public class UnknownFormatRequestException extends RuntimeException {
    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "유효하지 않은 타입의 문항입니다.";

    public UnknownFormatRequestException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
