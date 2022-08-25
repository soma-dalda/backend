package shop.dalda.exception.template;


import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends RuntimeException {

    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "템플릿을 찾을 수 없습니다.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}

