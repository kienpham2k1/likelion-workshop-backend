package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.likelion.dto.response.UserResponse;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Resource")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Get User")
    @GetMapping("/getUser")
    @ResponseStatus(HttpStatus.OK)
    public Optional<UserResponse> getUser() {
        Optional<UserResponse> userInfo = authenticationService.getCurrentUserInfo();
        return userInfo;
    }
}
