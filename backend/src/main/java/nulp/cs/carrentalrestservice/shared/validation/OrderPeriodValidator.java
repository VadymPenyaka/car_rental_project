package nulp.cs.carrentalrestservice.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nulp.cs.carrentalrestservice.shared.annotation.EndDate;
import nulp.cs.carrentalrestservice.shared.annotation.StartDate;
import nulp.cs.carrentalrestservice.shared.annotation.ValidOrderPeriod;
import nulp.cs.carrentalrestservice.shared.dto.request.CarSearchRequest;

import java.time.LocalDate;
import java.util.Arrays;

public class OrderPeriodValidator implements ConstraintValidator<ValidOrderPeriod, Object> {
    private static final int MAX_MONTHS_AHEAD = 3;

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) return true;

        LocalDate start = getAnnotatedDateField(obj, StartDate.class);
        LocalDate end = getAnnotatedDateField(obj, EndDate.class);

        LocalDate maxStartDate = LocalDate.now().plusMonths(MAX_MONTHS_AHEAD);

        if (start == null && end == null) return true;

        context.disableDefaultConstraintViolation();

        if (start == null || end == null) {
            addViolation(context, start == null ? "Start date must not be null." : "End date must not be null.",
                    start == null ? "startDate" : "endDate");
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

    private LocalDate getAnnotatedDateField(Object obj, Class<? extends java.lang.annotation.Annotation> annotation) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .filter(field -> field.getType().equals(LocalDate.class))
                .findFirst()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return (LocalDate) field.get(obj);
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                })
                .orElse(null);
    }

    private void addViolation(ConstraintValidatorContext context, String message, String field) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }

}
