package org.example.likelion.service.mail;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import org.example.likelion.config.MailServiceConfig;
import org.example.likelion.constant.InfoMessage;
import org.example.likelion.utils.APICallerUtils;
import org.example.likelion.utils.HtmlProcesserUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final APICallerUtils apiCaller;
    private final MailServiceConfig config;

    @Override
    public void sendOtpCode(String toEmail, String otp, String userName) throws MessagingException, IOException {

        Message msg = config.getJavaMailSender();

        //Config subject + date + destination
        msg.setFrom(new InternetAddress(config.mailAddress, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        msg.setSubject(InfoMessage.OTP_MESSAGE + otp);
        msg.setSentDate(new Date());

        //Get date format
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        String formattedDate = currentDate.format(formatter);

        //Config custom html file
        String htmlFilePath = "src/main/resources/static/emailOTP.html"; // Update the path to your emailOTP.html file
        String htmlTemplate = HtmlProcesserUtils.readHtmlTemplate(htmlFilePath);
        String htmlContent = HtmlProcesserUtils.replacePlaceholders(htmlTemplate, userName, formattedDate, otp);

        // Config content of mail
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlContent, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);

        //send mail
        Transport.send(msg);
    }
}
