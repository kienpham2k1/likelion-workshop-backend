package org.example.likelion.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponse {
    private String username;
    private String phoneNumber;
    private String addressLine;
}
