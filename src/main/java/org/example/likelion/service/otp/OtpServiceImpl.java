package org.example.likelion.service.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.constant.InfoMessage;
import org.example.likelion.service.sms.InfobipService;
import org.example.likelion.utils.OtpGeneratorUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpGeneratorUtils otpGenerator;
    private final InfobipService infobipService;

    @Override
    public Boolean sendOtpVisSms(String userName, String phoneNumber) {
        boolean rs;
        Integer otpValue = otpGenerator.generateOTP(userName);
        if (otpValue == -1) {
            log.error("OTP generator is not working...");
            return false;
        }
        log.info("Generated OTP: {}", otpValue);
        HttpStatusCode returnStatus = infobipService.sendOtpCode(phoneNumber, InfoMessage.OTP_MESSAGE + otpValue);
        rs = returnStatus.is2xxSuccessful();
        return rs;
    }

    @Override
    public Boolean sendOtpVisEmail(String userName, String email) {
        boolean rs = false;
        Integer otpValue = otpGenerator.generateOTP(userName);
        if (otpValue == -1) {
            log.error("OTP generator is not working...");
            return false;
        }
        log.info("Generated OTP: {}", otpValue);
        return rs;
    }

    @Override
    public Boolean validateOTP(String key, Integer otpNumber) {
        Integer cacheOTP = otpGenerator.getOPTByKey(key);
        if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}
