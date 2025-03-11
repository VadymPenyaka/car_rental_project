package nulp.cs.carrentalrestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidOrderException extends RuntimeException {
  public InvalidOrderException(String message) {
    super(message);
  }
}
