package org.example.likelion.service.otp;

public interface OtpService {
    Boolean sendOtpVisSms(String userName, String phoneNumber);

    Boolean sendOtpVisEmail(String userName, String email);

    Boolean validateOTP(String key, Integer otpNumber);

}
