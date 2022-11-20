
package shop.dalda.exception.order;

import org.springframework.http.HttpStatus;

public class OrderNotBelongToUserException extends RuntimeException {

    private final String ERROR_CODE = "T002";
    private final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "해당 주문에 권한을 가진 사용자가 아닙니다.";

    public OrderNotBelongToUserException() {
        super(MESSAGE);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}

