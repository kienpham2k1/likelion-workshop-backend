package org.example.likelion.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String phoneNumber;
    private String addressLine;
}