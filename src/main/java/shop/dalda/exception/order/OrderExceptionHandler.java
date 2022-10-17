package shop.dalda.exception.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Code: {}, Message: {}";

    // Order 관련 Exception
    @ExceptionHandler(OrderNotBelongToUserException.class)
    public ResponseEntity<String> OrderNotBelongToUserException(OrderNotBelongToUserException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> OrderNotFoundException(OrderNotFoundException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }
}
