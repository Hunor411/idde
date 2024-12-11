package edu.bbte.idde.dhim2228.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Event extends BaseEntity {
    private String name;
    private String location;
    private LocalDateTime date;
    private Boolean isOnline;
    private String description;
    private Integer attendeesCount;
}