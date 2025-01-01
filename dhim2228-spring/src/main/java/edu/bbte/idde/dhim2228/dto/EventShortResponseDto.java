package edu.bbte.idde.dhim2228.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventShortResponseDto {
    private Long id;
    private String name;
    private LocalDateTime date;
}
