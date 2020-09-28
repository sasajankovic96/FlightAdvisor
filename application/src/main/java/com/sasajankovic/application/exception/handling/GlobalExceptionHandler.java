package com.sasajankovic.application.exception.handling;

import com.sasajankovic.application.dtos.BadRequestDto;
import com.sasajankovic.application.dtos.ConflictDto;
import com.sasajankovic.domain.exceptions.ConflictException;
import com.sasajankovic.domain.exceptions.EntityNotFoundException;
import com.sasajankovic.domain.exceptions.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors =
                bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
        BadRequestDto badRequest = new BadRequestDto(errors);
        return ResponseEntity.badRequest().body(badRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity not found: ", ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbiddenException(ForbiddenException ex) {
        log.error("Action is forbidden, no permission to perform this action: ", ex);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ConflictDto> conflictException(ConflictException ex) {
        log.error("Conflicting exception: ", ex);
        return new ResponseEntity<>(new ConflictDto(ex.getMessage()), HttpStatus.CONFLICT);
    }
}
