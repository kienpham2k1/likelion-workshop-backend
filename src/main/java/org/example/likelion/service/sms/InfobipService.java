package org.example.likelion.service.sms;

public interface InfobipService {
    void sendOtpCode(String toPhoneNumber, String message);
}
