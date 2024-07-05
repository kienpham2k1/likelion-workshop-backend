package org.example.likelion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.example.likelion.dto.auth.Role;

import java.io.Serializable;

/**
 * DTO for {@link org.example.likelion.model.Admin}
 */
@Value
public class AdminRequest implements Serializable {
    @NotBlank
    String username;
    @NotNull
    @NotBlank
    String password;
    @NotNull
    @JsonIgnore
    Role role = Role.ADMIN;
}