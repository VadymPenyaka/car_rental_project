package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate expiryDate, ConstraintValidatorContext constraintValidatorContext) {
        return expiryDate.isAfter(LocalDate.now());
    }
}
