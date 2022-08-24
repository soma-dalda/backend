package shop.dalda.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Code: {}, Message: {}";

    @ExceptionHandler(TemplateInvalidException.class)
    public ResponseEntity<String> templateInvalidException(TemplateInvalidException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getErrorCode());
    }

    @ExceptionHandler(TemplateNotFoundException.class)
    public ResponseEntity<String> templateNotFoundException(TemplateNotFoundException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getErrorCode());
    }

    @ExceptionHandler(TemplateNotBelongToUserException.class)
    public ResponseEntity<String> templateNotBelongToUserException(TemplateNotBelongToUserException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getErrorCode());
    }

}
