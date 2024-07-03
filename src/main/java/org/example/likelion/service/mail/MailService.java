package org.example.likelion.service.mail;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface MailService {
    void sendOtpCode(String toEmail, String otp, String userName) throws MessagingException, IOException;
}
