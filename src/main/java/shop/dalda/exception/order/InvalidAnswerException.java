package shop.dalda.exception.order;

import org.springframework.http.HttpStatus;

public class InvalidAnswerException extends RuntimeException {

    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "올바르지 않은 답변입니다.";

    public InvalidAnswerException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
