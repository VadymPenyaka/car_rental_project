package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniquePassportNumber;
import nulp.cs.carrentalrestservice.service.PassportService;

@RequiredArgsConstructor
public class UniquePassportNumberValidator implements ConstraintValidator<UniquePassportNumber, String> {
    private final PassportService passportService;
    @Override
    public boolean isValid(String passportNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !passportService.existsByDocumentNumber(passportNumber);
    }
}
