package edu.bbte.idde.dhim2228.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Object messages;
    private int status;
    private LocalDateTime timestamp;
}
