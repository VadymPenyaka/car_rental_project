package nulp.cs.carrentalrestservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Your password is invalid!" +
            "It must contain 8 to 18 characters" +
            "One digit, one special symbol, one small and one capital letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
