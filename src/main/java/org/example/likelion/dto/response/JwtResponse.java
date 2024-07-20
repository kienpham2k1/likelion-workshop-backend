package org.example.likelion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
