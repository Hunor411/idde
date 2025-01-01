package edu.bbte.idde.dhim2228.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private Object messages;
    private int status;
    private LocalDateTime timestamp;
}
