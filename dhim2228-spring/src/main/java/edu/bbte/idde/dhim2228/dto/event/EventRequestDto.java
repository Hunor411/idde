package edu.bbte.idde.dhim2228.dto.event;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @DateTimeFormat
    private String date;

    @NotNull
    private Boolean isOnline;

    @Size(max = 500)
    private String description;
}
