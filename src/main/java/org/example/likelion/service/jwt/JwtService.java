package org.example.likelion.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);

    String generateRefreshToken(UserDetails userDetails);

    String getUserNameFromJwtToken(String token);

    String getUserNameFromHeaderBearerToken(String token);

    boolean validateJwtToken(String authToken);

    boolean isTokenValid(String token, UserDetails userDetails);
}
