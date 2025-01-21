package edu.bbte.idde.dhim2228.validators;

import edu.bbte.idde.dhim2228.anotations.PasswordMatches;
import edu.bbte.idde.dhim2228.dto.user.UserRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRequestDto> {
    private String message;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRequestDto userRequestDto, ConstraintValidatorContext context) {
        boolean passwordsMatch = userRequestDto.getPassword().equals(userRequestDto.getConfirmPassword());

        if (!passwordsMatch) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode("password")
                    .addConstraintViolation();
        }

        return passwordsMatch;
    }
}
