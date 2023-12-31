package org.dscatalog.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandartError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
        StandartError error = fillBody(e, request, HttpStatus.NOT_FOUND, "Entity not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandartError> databaseIntegrity(DatabaseException e, HttpServletRequest request){
        StandartError error = fillBody(e, request, HttpStatus.BAD_REQUEST, "Database exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    public StandartError fillBody(RuntimeException e, HttpServletRequest request, HttpStatus status, String errorMessage ){
        StandartError error = new StandartError();
        error.setTimestamp(Instant.now());
        error.setPath(request.getRequestURI());
        error.setMessage(e.getMessage());
        error.setStatus(status.value());
        error.setError(errorMessage);
        return error;
    }
}
