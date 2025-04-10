package nulp.cs.carrentalrestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CategoryVerificationException extends RuntimeException {
    public CategoryVerificationException(String message) {
        super(message);
    }
}
