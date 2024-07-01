package org.example.likelion.service.otp;

public interface OtpService {
    public Boolean generateOtp(String key);
    Boolean validateOTP(String key, Integer otpNumber);

}
