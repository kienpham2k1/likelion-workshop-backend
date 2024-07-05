package org.example.likelion.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponse {
    private UserResponse user;
    private JwtResponse token;
}
