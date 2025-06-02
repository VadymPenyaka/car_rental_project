package nulp.cs.carrentalrestservice.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CategoryExperienceVerificationException extends RuntimeException {
    public CategoryExperienceVerificationException(String message) {
        super(message);
    }
}
