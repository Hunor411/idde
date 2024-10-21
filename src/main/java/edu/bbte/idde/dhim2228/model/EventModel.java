package edu.bbte.idde.dhim2228.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventModel extends BaseModel {
    private String name;
    private String location;
    private LocalDateTime date;
    private Boolean isOnline;
    private String description;
    private Integer attendeesCount;
}
