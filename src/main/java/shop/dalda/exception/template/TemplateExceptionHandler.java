package shop.dalda.exception.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "shop.dalda.template")
public class TemplateExceptionHandler {

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
}
