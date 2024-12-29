package edu.bbte.idde.dhim2228.dto.event;

import edu.bbte.idde.dhim2228.model.Role;
import edu.bbte.idde.dhim2228.model.Status;
import lombok.Data;

@Data
public class EventUserDetailsResponseDto {
    private Long userId;
    private Role role;
    private Status status;
}
