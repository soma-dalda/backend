package shop.dalda.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Code: {}, Message: {}";

    // 공통 Exception
    @ExceptionHandler(UnknownFormatRequestException.class)
    public ResponseEntity<String> UnknownFormatRequestException(UnknownFormatRequestException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(e.getMessage());
    }

    // Validate Exception
    @ExceptionHandler()
    public ResponseEntity<ExceptionResponse> UnknownFormatRequestException(MethodArgumentNotValidException e) {
        log.warn("Class: {}, Message: {}, BindingResult: {}",e.getClass().getSimpleName(), e.getMessage(), e.getBindingResult());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            sb.append("[fieldName]: ").append(fieldError.getField()).append(", [message]: ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.badRequest().body(new ExceptionResponse(sb.toString()));
    }

}
