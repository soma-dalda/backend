package shop.dalda.exception.template;

import org.springframework.http.HttpStatus;

public class TemplateInvalidException extends RuntimeException {

    private final String ERROR_CODE = "T001";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "템플릿의 형식이 올바르지 않습니다.";

    public TemplateInvalidException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
