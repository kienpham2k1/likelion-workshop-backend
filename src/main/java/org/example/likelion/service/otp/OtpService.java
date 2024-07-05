package org.example.likelion.service.otp;

public interface OtpService {
    Boolean sendOtpVisSms();

    Boolean sendOtpVisEmail();

    Boolean validateOTP(Integer otpNumber);

}
