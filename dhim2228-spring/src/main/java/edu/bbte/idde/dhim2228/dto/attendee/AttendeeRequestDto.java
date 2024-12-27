package edu.bbte.idde.dhim2228.dto.attendee;

import edu.bbte.idde.dhim2228.anotations.ValidRole;
import edu.bbte.idde.dhim2228.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendeeRequestDto {
    @NotNull
    private Long userId;

    @NotNull
    @ValidRole
    private Role role;
}
