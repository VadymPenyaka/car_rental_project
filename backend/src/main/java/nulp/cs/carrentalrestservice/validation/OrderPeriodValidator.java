package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nulp.cs.carrentalrestservice.annotation.ValidOrderPeriod;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;

import java.time.LocalDate;

public class OrderPeriodValidator implements ConstraintValidator<ValidOrderPeriod, CarSearchRequest> {
    private static final int MAX_MONTHS_AHEAD = 3;

    @Override
    public boolean isValid(CarSearchRequest request, ConstraintValidatorContext context) {
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();
        LocalDate maxStartDate = LocalDate.now().plusMonths(MAX_MONTHS_AHEAD);

        if (start == null && end == null) return true;

        context.disableDefaultConstraintViolation();

        if (start == null || end == null) {
            addViolation(context, start == null ? "Start date must not be null." : "End date must not be null.", start == null ? "startDate" : "endDate");
            return false;
        }

        if (start.isBefore(LocalDate.now())) {
            addViolation(context, "Start date must not be in the past.", "startDate");
            return false;
        }

        if (start.isAfter(maxStartDate)) {
            addViolation(context, "Start date must be within 3 months from today.", "startDate");
            return false;
        }

        if (end.isAfter(start.plusMonths(MAX_MONTHS_AHEAD))) {
            addViolation(context, "End date must be within 3 months from start date.", "endDate");
            return false;
        }


        if (start.isAfter(end)) {
            addViolation(context, "Start date must not be later than end date.", "startDate");
            return false;
        }

        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message, String field) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }

}
