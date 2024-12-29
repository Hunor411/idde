package edu.bbte.idde.dhim2228.controller.controlleradvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import edu.bbte.idde.dhim2228.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException e) {
        log.debug("ConstraintViolationException occurred", e);
        List<String> errors = e.getConstraintViolations().stream()
                .map(it -> it.getPropertyPath().toString() + " " + it.getMessage()).toList();

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.debug("MethodArgumentNotValidException occurred", e);
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + " " + it.getDefaultMessage()).toList();

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.debug("HttpMessageNotReadableException occurred", e);

        String message = "Invalid request payload.";
        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            String invalidValue = invalidFormatException.getValue().toString();
            List<String> path = invalidFormatException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.toList());
            String fieldName = String.join(".", path);

            message = String.format(
                    "Field '%s' has an invalid value '%s'.",
                    fieldName, invalidValue);
        }

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                message,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
}

