package nulp.cs.carrentalrestservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nulp.cs.carrentalrestservice.validation.UniqueEmailVlidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailVlidator.class)
public @interface UniqueEmail {
    String message() default "User with this email is already registered!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
