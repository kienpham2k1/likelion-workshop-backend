package org.example.likelion.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.example.likelion.dto.auth.Role;

import java.io.Serializable;

/**
 * DTO for {@link org.example.likelion.model.Admin}
 */
@Value
public class AdminResponse implements Serializable {
    @NotBlank
    String username;
}