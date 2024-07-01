package org.example.likelion.service.sms;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.InfoMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioServiceImpl implements TwilioService {
    @Value("${twilio.account_sid}")
    public String ACCOUNT_SID;
    @Value("${twilio.auth_token}")
    public String AUTH_TOKEN;
    @Value("${twilio.from_phone_number}")
    public String FROM_PHONE_NUMBER;
    @Value("${twilio.to_phone_number}")
    public String TO_PHONE_NUMBER;

    @Override
    public Message sendOtpCode() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber(TO_PHONE_NUMBER),
                        new PhoneNumber(FROM_PHONE_NUMBER),
                        InfoMessage.OTP_MESSAGE)
                .create();

        System.out.println(message.getSid());
        return message;
    }
}
