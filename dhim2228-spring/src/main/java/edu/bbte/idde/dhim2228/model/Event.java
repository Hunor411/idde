package edu.bbte.idde.dhim2228.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "events")
public class Event extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "description")
    private String description;

    @Column(name = "attendees_count")
    private Integer attendeesCount;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Attendee> attendees;
}