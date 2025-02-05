package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniqueEmail;
import nulp.cs.carrentalrestservice.service.CustomerService;


@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator <UniqueEmail, String> {
    private final CustomerService customerService;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !customerService.isEmailUsed(email);
    }
}
