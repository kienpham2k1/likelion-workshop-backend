package org.example.likelion.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Map;

public interface IJwtService {
    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);

    String generateRefreshToken(UserDetails userDetails);

    String getUserNameFromJwtToken(String token);
    boolean validateJwtToken(String authToken);
}
