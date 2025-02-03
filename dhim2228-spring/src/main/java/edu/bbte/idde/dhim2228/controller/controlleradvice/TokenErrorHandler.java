package edu.bbte.idde.dhim2228.controller.controlleradvice;

import edu.bbte.idde.dhim2228.controller.exceptions.InvalidToken;
import edu.bbte.idde.dhim2228.controller.exceptions.MissingToken;
import edu.bbte.idde.dhim2228.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class TokenErrorHandler {
    @ExceptionHandler(MissingToken.class)
    public ResponseEntity<ErrorResponseDto> handleMissingToken(MissingToken e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidToken(InvalidToken e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }
}
