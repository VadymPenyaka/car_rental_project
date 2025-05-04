package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniqueDriverLicenseNumber;
import nulp.cs.carrentalrestservice.service.person.customer.DriverLicenseService;

@RequiredArgsConstructor
public class UniqueLicenseNumberValidator implements ConstraintValidator <UniqueDriverLicenseNumber, String> {
    private final DriverLicenseService driverLicenseService;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !driverLicenseService.existsByDocumentNumber(s);
    }
}
