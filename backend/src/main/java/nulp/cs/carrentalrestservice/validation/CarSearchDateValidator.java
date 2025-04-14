package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nulp.cs.carrentalrestservice.annotation.ValidDateRange;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;

import java.time.LocalDate;

public class CarSearchDateValidator implements ConstraintValidator<ValidDateRange, CarSearchRequest> {

    @Override
    public boolean isValid(CarSearchRequest request, ConstraintValidatorContext context) {
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();

        if (start != null && end != null) {
            return !end.isBefore(start);
        }

        return true;
    }
}
