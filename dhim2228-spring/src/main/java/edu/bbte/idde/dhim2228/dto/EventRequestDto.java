package edu.bbte.idde.dhim2228.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EventRequestDto {
    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @Size(min = 3, max = 30)
    private String location;

    @DateTimeFormat
    private String date;

    private Boolean isOnline;

    @Size(max = 500)
    private String description;

    @Positive
    private Integer attendeesCount;
}
