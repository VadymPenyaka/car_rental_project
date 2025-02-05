package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniquePhoneNumber;
import nulp.cs.carrentalrestservice.service.CustomerService;

@RequiredArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    private final CustomerService customerService;
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !customerService.isPhoneNumberUsed(phoneNumber);
    }
}
