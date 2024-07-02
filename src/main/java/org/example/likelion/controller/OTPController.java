package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.service.jwt.JwtService;
import org.example.likelion.service.otp.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
@Slf4j
@Tag(name = "OTP Verification Service")
public class OTPController {
    private final OtpService otpService;
    private final JwtService jwtService;
//    private final UserService userService;

    @Operation(summary = "Send OTP by SMS")
    @PostMapping("/sendSms")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendSms(@RequestParam String phoneNumber, @RequestHeader(name = "Authorization") String token) {
        String name = jwtService.getUserNameFromHeaderBearerToken(token);

        otpService.sendOtpVisSms(name, "+84" + phoneNumber);
    }

    @Operation(summary = "Send OTP by Mail")
    @PostMapping("/sendEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMail(@RequestParam String email, @RequestHeader(name = "Authorization") String token) {
        String name = jwtService.getUserNameFromHeaderBearerToken(token);
        otpService.sendOtpVisEmail(name, email);
    }

    @Operation(summary = "Verify OTP")
    @PostMapping("/verify")
    public Boolean verify(@RequestParam Integer otp, @RequestHeader(name = "Authorization") String token) {
        String name = jwtService.getUserNameFromHeaderBearerToken(token);
        return otpService.validateOTP(name, otp);
    }

}