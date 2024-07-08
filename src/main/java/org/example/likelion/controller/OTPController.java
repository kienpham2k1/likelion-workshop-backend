package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.service.otp.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp")
@Slf4j
@Tag(name = "OTP Verification Service")
public class OTPController {
    private final OtpService otpService;

    @Operation(summary = "Send OTP by SMS")
    @GetMapping("/sendSms")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean sendSms() {
        return otpService.sendOtpVisSms();
    }

    @Operation(summary = "Send OTP by Mail")
    @GetMapping("/sendEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean sendMail() {
        return otpService.sendOtpVisEmail();
    }

    @Operation(summary = "Verify OTP")
    @PostMapping("/verify")
    public Boolean verify(@RequestParam Integer otp) {
        return otpService.validateOTP(otp);
    }

}