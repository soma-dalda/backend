package shop.dalda.exception;

import org.springframework.context.annotation.Profile;

public class OAuthProcessingException extends RuntimeException {

    public OAuthProcessingException(String message) {
        super(message);
    }
}
