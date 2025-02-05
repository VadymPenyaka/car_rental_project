package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniquePassportId;
import nulp.cs.carrentalrestservice.service.CustomerService;

@RequiredArgsConstructor
public class UniquePassportIdValidator implements ConstraintValidator<UniquePassportId, String> {
    private final CustomerService customerService;
    @Override
    public boolean isValid(String passportId, ConstraintValidatorContext constraintValidatorContext) {
        return !customerService.isPassportIdUsed(passportId);
    }
}
