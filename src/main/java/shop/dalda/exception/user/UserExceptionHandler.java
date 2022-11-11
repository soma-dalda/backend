package shop.dalda.exception.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.dalda.exception.ExceptionResponse;
import shop.dalda.exception.user.auth.RefreshTokenException;
import shop.dalda.exception.user.auth.UserNotFoundException;
import shop.dalda.exception.user.company.DomainDuplicatedException;
import shop.dalda.exception.user.company.DomainNotFoundException;
import shop.dalda.exception.user.company.InvalidDomainException;

@RestControllerAdvice(basePackages = "shop.dalda.user")
public class UserExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Message: {}";
    private final Logger log = LoggerFactory.getLogger(UserExceptionHandler.class);

    /**
     * User Auth Exceptions
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> tokenException(RefreshTokenException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> userNotFound(UserNotFoundException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }
    /**
     * Auth Exceptions
     */

    /**
     * Company Exceptions
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> domainNotFound(DomainNotFoundException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> invalidDomain(InvalidDomainException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> duplicatedDomain(DomainDuplicatedException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Company Exceptions
     */
}
