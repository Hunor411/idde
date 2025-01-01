package edu.bbte.idde.dhim2228.controller.controlleradvice;

import edu.bbte.idde.dhim2228.dto.ErrorResponseDto;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class InternalServerErrorHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleServiceException(ServiceException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }
}
