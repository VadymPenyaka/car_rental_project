package nulp.cs.carrentalrestservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nulp.cs.carrentalrestservice.validation.UniquePassportIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  UniquePassportIdValidator.class)
public @interface UniquePassportId {
    String message() default "User with this passport id is already registered!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
