package edu.bbte.idde.dhim2228.dto.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventResponseDto {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime date;
    private Boolean isOnline;
    private String description;
    private List<EventUserDetailsResponseDto> attendees;
}