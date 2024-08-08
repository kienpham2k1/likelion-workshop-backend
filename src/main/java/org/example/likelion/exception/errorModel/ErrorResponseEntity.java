package org.example.likelion.exception.errorModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponseEntity {
    private HttpStatus status;
    String message;
    @JsonInclude(value = Include.NON_NULL)
    Map<String, String> validationErrors;
    private String description;
    @lombok.Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
