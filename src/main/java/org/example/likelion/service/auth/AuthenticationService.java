package org.example.likelion.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.UserRegisterResponse;
import org.example.likelion.dto.response.UserResponse;

import java.io.IOException;
import java.util.Optional;

public interface AuthenticationService {
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    UserRegisterResponse authenticate(LoginRequest loginRequest);

    void saveUserToken(UserDetailsImpl userDetails, String jwtResponse);

    void revokeAllUserTokens(UserDetailsImpl user);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response) throws IOException;

    Optional<UserDetailsImpl> getCurrentUser();

    Optional<UserResponse> getCurrentUserInfo();
}
