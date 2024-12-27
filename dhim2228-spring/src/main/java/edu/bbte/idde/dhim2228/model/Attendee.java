package edu.bbte.idde.dhim2228.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "attendees")
public class Attendee extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private Status userStatus = Status.INVITED;
}
