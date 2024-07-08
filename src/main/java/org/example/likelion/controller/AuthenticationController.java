package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.UserRegisterResponse;
import org.example.likelion.repository.UserRepository;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authenticate Resource")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    UserRepository userRepository;


    @Operation(summary = "Sign Up")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse registerUser(@RequestBody @Valid UserRegisterRequest signUpRequest) {
        return authenticationService.register(signUpRequest);
    }

    @Operation(summary = "Sign In")
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public UserRegisterResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticate(loginRequest);
    }

    @Operation(summary = "Refresh Token")
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestParam String userName) {
        authenticationService.logout(userName);
    }
}
