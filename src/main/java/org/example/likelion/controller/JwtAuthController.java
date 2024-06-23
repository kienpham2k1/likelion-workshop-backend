package org.example.likelion.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.likelion.dto.mapper.IUserMapper;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.dto.response.UserRegisterResponse;
import org.example.likelion.service.auth.AuthenticationService;
import org.example.likelion.service.jwt.JwtService;
import org.example.likelion.model.User;
import org.example.likelion.repository.UserRepository;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class JwtAuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse registerUser(@RequestBody @Valid UserRegisterRequest signUpRequest) {
        return authenticationService.register(signUpRequest);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = authenticationService.authenticate(loginRequest);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
