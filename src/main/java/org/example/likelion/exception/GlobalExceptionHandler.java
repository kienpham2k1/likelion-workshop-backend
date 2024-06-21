package org.example.likelion.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //404
    @ExceptionHandler({
            ResourceNotFoundException.class,
            EntityNotFoundException.class,
            OutOfStockProductException.class
    })
    public <T extends RuntimeException> ResponseEntity<ApiError> resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Resource not found: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Data integrity violation exception: %s".formatted(message.getDescription()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    //409
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ApiError> duplicateRecordExceptionHandler(DuplicateRecordException ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Duplicate record exception: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s".formatted(message));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}