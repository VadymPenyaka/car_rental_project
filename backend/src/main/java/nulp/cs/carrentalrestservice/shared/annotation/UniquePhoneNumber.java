package nulp.cs.carrentalrestservice.shared.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nulp.cs.carrentalrestservice.shared.validation.UniquePhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
public @interface UniquePhoneNumber {
    String message() default "User with this phone number is already registered!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
