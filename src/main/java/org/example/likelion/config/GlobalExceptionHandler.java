package org.example.likelion.config;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.DuplicateRecordException;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.exception.OutOfStockProductException;
import org.example.likelion.exception.ResourceNotFoundException;
import org.example.likelion.exception.errorModel.ErrorResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler({ResourceNotFoundException.class
            , EntityNotFoundException.class
            , OutOfStockProductException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T extends RuntimeException> ErrorResponseEntity resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
        return errorResponse;
    }

    //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseEntity dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest request) {
        var message = ex.getSuppressed();
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
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
        log.error("Exception: %s, %s".formatted(ex.getClass().getSimpleName(), errors.toString()));
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
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
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

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseEntity auth(DuplicateRecordException ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
        return errorResponse;
    }

    @ExceptionHandler({JwtException.class})
    public <T extends RuntimeException> ErrorResponseEntity jwtExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ErrorMessage.JWT_TOKEN_INVALID)
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
        return errorResponse;
    }


    //403
    @ExceptionHandler({AccessDeniedException.class})
    public <T extends RuntimeException> ErrorResponseEntity accessDeniedExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
        return errorResponse;
    }
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseEntity SQLException(SQLException ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getDescription(), ex.getMessage()));
        return errorResponse;
    }
}