package org.example.likelion.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.likelion.exception.errorModel.ErrorResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class, OutOfStockProductException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T extends RuntimeException> ErrorResponseEntity resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Resource not found: %s".formatted(errorResponse));
        return errorResponse;
    }

    //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseEntity dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Data integrity violation exception: %s".formatted(errorResponse.getDescription()));
        return errorResponse;
    }

    // Handler validate error
    // 404
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error(errors.toString());
        return ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validate error")
                .validationErrors(errors)
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
    }

    //500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseEntity globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now()).build();
        log.error("Exception: %s".formatted(errorResponse));
        log.error(ex.getMessage(), ex);
        return errorResponse;
    }

    //409
    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseEntity duplicateRecordExceptionHandler(DuplicateRecordException ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Duplicate record exception: %s".formatted(errorResponse));
        return errorResponse;
    }
}