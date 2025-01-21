package edu.bbte.idde.dhim2228.anotations;

import edu.bbte.idde.dhim2228.validators.PasswordMatchesValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {
    String message() default "does not match the confirm password";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
