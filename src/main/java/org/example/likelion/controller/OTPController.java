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

    @Operation(summary = "Send OTP by SMS")
    @PostMapping("/sendSms")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendSms() {
        otpService.sendOtpVisSms();
    }

    @Operation(summary = "Send OTP by Mail")
    @PostMapping("/sendEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMail() {
        otpService.sendOtpVisEmail();
    }

    @Operation(summary = "Verify OTP")
    @PostMapping("/verify")
    public Boolean verify(@RequestParam Integer otp) {
        return otpService.validateOTP(otp);
    }

}