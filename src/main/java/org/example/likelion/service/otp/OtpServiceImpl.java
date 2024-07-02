package org.example.likelion.service.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.utils.OtpGeneratorUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpGeneratorUtils otpGenerator;

    @Override
    public Boolean generateOtp(String key) {
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1) {
            log.error("OTP generator is not working...");
            return false;
        }
        log.info("Generated OTP: {}", otpValue);
        return true;
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
