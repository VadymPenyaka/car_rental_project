package nulp.cs.carrentalrestservice.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.annotation.UniquePhoneNumber;
import nulp.cs.carrentalrestservice.modules.person.PersonService;

@RequiredArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    private final PersonService personService;
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !personService.isPhoneNumberUsed(phoneNumber);
    }
}
