package edu.bbte.idde.dhim2228.anotations;

import edu.bbte.idde.dhim2228.validators.PasswordValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "must be at least 8 characters long, contain one uppercase letter, and one number.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
