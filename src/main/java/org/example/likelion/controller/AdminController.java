package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IAdminMapper;
import org.example.likelion.dto.request.AdminRequest;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.response.AdminResponse;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse create(@RequestBody @Valid AdminRequest request) {
        return adminService.create(request);

    }

    @Operation(summary = "Sign In")
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return adminService.authenticate(loginRequest);
    }

    @Operation(summary = "Refresh Token")
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        adminService.refreshToken(request, response);
    }
}
