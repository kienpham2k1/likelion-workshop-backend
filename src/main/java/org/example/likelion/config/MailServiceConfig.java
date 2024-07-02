package org.example.likelion.config;


import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailServiceConfig {
    @Value("${mail.mail_address}")
    public String MAIL_ADDRRESS;
    @Value("${mail.mail_app_password}")
    public String MAIL_APP_PASSWORD;

    @Bean
    public Message getJavaMailSender() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_ADDRRESS, MAIL_APP_PASSWORD);
            }
        });
        Message msg = new MimeMessage(session);
        return msg;
    }
}
