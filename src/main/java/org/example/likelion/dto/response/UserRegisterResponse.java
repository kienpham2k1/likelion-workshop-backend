package org.example.likelion.dto.response;

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
    private String email;
    private Boolean verify;
}
