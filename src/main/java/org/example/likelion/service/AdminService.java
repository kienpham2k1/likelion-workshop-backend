package org.example.likelion.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.likelion.dto.request.AdminRequest;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.response.AdminResponse;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.model.Admin;

import java.io.IOException;

public interface AdminService {
    AdminResponse create(AdminRequest entity);

    JwtResponse authenticate(LoginRequest loginRequest);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response) throws IOException;
}