package edu.bbte.idde.dhim2228.validators;

import edu.bbte.idde.dhim2228.anotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.matches(PASSWORD_PATTERN);
    }
}
