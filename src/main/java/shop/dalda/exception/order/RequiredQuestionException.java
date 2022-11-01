package shop.dalda.exception.order;

import org.springframework.http.HttpStatus;

public class RequiredQuestionException extends RuntimeException {

    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "필수 문항이 비어있습니다.";

    public RequiredQuestionException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
