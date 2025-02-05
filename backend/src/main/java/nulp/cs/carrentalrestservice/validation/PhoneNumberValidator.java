package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.ValidPhoneNumber;
import nulp.cs.carrentalrestservice.service.CustomerService;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private Pattern pattern;
    private final CustomerService customerService;
    private static final String PHONE_PATTERN =
            "^\\+?[0-9]\\d{1,14}$";

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        pattern = Pattern.compile(PHONE_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return false;
        }
        return pattern.matcher(phoneNumber).matches();
    }
}
