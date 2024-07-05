package org.example.likelion.service.sms;

import com.twilio.rest.api.v2010.account.Message;

public interface TwilioService {
    Message sendOtpCode(String toPhoneNumber, String messageSend);
}
