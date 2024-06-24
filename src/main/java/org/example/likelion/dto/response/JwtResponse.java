package org.example.likelion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
//    @JsonProperty("access_token")
    private String accessToken;
//    @JsonProperty("access_token")
    private String refreshToken;
}
