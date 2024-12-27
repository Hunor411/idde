package edu.bbte.idde.dhim2228.dto.attendee;

import edu.bbte.idde.dhim2228.model.Attendee;
import edu.bbte.idde.dhim2228.model.Role;
import edu.bbte.idde.dhim2228.model.Status;

public class AttendeeResponseDto extends Attendee {
    private String username;
    private Role role;
    private Status userStatus;
}
