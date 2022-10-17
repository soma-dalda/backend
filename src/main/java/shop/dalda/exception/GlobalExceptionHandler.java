package shop.dalda.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.dalda.exception.order.OrderNotBelongToUserException;
import shop.dalda.exception.order.OrderNotFoundException;
import shop.dalda.exception.template.TemplateInvalidException;
import shop.dalda.exception.template.TemplateNotBelongToUserException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.exception.template.UnsupportedTypeContentException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Code: {}, Message: {}";

    // Template 관련 Exception
    @ExceptionHandler(TemplateInvalidException.class)
    public ResponseEntity<String> templateInvalidException(TemplateInvalidException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }

    @ExceptionHandler(TemplateNotBelongToUserException.class)
    public ResponseEntity<String> templateNotBelongToUserException(TemplateNotBelongToUserException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }

    @ExceptionHandler(TemplateNotFoundException.class)
    public ResponseEntity<String> templateNotFoundException(TemplateNotFoundException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }

    @ExceptionHandler(UnsupportedTypeContentException.class)
    public ResponseEntity<String> UnsupportedTypeContentException(UnsupportedTypeContentException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }

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

    // 공통 Exception
    @ExceptionHandler(UnknownFormatRequestException.class)
    public ResponseEntity<String> UnknownFormatRequestException(UnknownFormatRequestException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }
}
