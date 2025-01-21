package edu.bbte.idde.dhim2228.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@Table(name = "attendees")
public class Attendee {
    @EmbeddedId
    private AttendeeId id;

    @ManyToOne
    @MapsId("eventId")
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @MapsId("userId")
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private Status userStatus = Status.INVITED;
}
