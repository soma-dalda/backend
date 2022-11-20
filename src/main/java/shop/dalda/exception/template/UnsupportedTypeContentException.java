package shop.dalda.exception.template;

import org.springframework.http.HttpStatus;

public class UnsupportedTypeContentException extends RuntimeException {
    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "올바르지 않은 형태의 Request Body입니다.(Json Parsing Error)";

    public UnsupportedTypeContentException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
