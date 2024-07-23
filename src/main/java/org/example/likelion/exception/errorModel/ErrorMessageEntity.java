package org.example.likelion.exception.errorModel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorMessageEntity {

    private String message;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
