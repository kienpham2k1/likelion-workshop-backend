package org.example.likelion.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.likelion.dto.request.AdminRequest;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.response.AdminResponse;
import org.example.likelion.dto.response.JwtResponse;

import java.io.IOException;

public interface AdminAuthenticationService {
    AdminResponse create(AdminRequest entity);

    JwtResponse authenticate(LoginRequest loginRequest);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response) throws IOException;
}