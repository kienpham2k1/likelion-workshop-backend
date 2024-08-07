package org.example.likelion.dto.response;

import lombok.*;
import org.example.likelion.dto.auth.Role;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String phoneNumber;
    private String addressLine;
    private String email;
    private Boolean verify;
    private Role role;
}
