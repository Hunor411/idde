package edu.bbte.idde.dhim2228.validators;

import edu.bbte.idde.dhim2228.anotations.ValidRole;
import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.model.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, AttendeeRequestDto> {
    @Override
    public boolean isValid(AttendeeRequestDto attendeeRequestDto, ConstraintValidatorContext context) {
        return isValidRole(attendeeRequestDto.getRole());
    }

    private boolean isValidRole(Role role) {
        for (Role validRole : Role.values()) {
            if (validRole == role) {
                return true;
            }
        }
        return false;
    }
}
