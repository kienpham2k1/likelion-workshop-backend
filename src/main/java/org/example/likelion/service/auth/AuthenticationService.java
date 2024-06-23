package org.example.likelion.service.auth;

import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.dto.response.UserRegisterResponse;
import org.example.likelion.model.User;

public interface AuthenticationService {
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    JwtResponse authenticate(LoginRequest loginRequest);

    JwtResponse saveUserToken();

    void revokeAllUserTokens();

    void refreshToken();

}
