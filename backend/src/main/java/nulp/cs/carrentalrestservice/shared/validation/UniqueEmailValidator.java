package nulp.cs.carrentalrestservice.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.annotation.UniqueEmail;
import nulp.cs.carrentalrestservice.modules.person.service.PersonService;


@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator <UniqueEmail, String> {
    private final PersonService personService;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !personService.isEmailUsed(email);
    }
}
