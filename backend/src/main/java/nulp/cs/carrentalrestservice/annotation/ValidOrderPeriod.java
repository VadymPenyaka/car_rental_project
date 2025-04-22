package nulp.cs.carrentalrestservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nulp.cs.carrentalrestservice.validation.OrderPeriodValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderPeriodValidator.class)
public @interface ValidOrderPeriod {
    String message() default "Invalid start or end date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
