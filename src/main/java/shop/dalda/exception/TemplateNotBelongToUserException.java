package shop.dalda.exception;

import org.springframework.http.HttpStatus;

public class TemplateNotBelongToUserException extends RuntimeException {

    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "해당 템플릿을 등록한 사용자가 아닙니다.";

    public TemplateNotBelongToUserException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
