package org.example.likelion.service.sms;

import org.springframework.http.HttpStatusCode;

public interface InfobipService {
    HttpStatusCode sendOtpCode(String toPhoneNumber, String message);
}
