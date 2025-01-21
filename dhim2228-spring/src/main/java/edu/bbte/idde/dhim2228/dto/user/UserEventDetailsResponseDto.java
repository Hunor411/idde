package edu.bbte.idde.dhim2228.dto.user;

import edu.bbte.idde.dhim2228.model.Role;
import edu.bbte.idde.dhim2228.model.Status;
import lombok.Data;

@Data
public class UserEventDetailsResponseDto {
    Long eventId;
    private Role role;
    private Status status;
}
