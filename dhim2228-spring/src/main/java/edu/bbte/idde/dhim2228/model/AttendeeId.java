package edu.bbte.idde.dhim2228.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AttendeeId {
    private Long eventId;
    private Long userId;
}
