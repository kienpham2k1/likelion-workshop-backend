package org.example.likelion.service.sms;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.request.infobip.InfobipRequest;
import org.example.likelion.dto.request.infobip.Message;
import org.example.likelion.dto.request.infobip.Recipient;
import org.example.likelion.utils.APICallerUtils;
import org.example.likelion.utils.OtpGeneratorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class InfobipServiceImpl implements InfobipService {
    private final APICallerUtils apiCaller;
    private final OtpGeneratorUtils otpGenerator;
    @Value("${infobip.url}")
    private String apiUrl;
    @Value("${infobip.api_key}")
    private String apiKey;
    private final String FROM = "ServiceSMS";

    @Override
    public void sendOtpCode(String toPhoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "App " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        InfobipRequest messages = InfobipRequest.builder()
                .messages(Collections.singletonList(Message
                        .builder()
                        .destinations(Collections.singletonList(Recipient
                                .builder()
                                .to(toPhoneNumber)
                                .build()))
                        .from(FROM)
                        .text(message)
                        .build()))
                .build();
        apiCaller.callApi(apiUrl, messages, headers, HttpMethod.POST, String.class);
    }
}
