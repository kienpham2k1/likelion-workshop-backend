package org.example.likelion.service.otp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.constant.InfoMessage;
import org.example.likelion.dto.mapper.IUserMapper;
import org.example.likelion.dto.response.UserResponse;
import org.example.likelion.model.User;
import org.example.likelion.repository.UserRepository;
import org.example.likelion.service.auth.AuthenticationService;
import org.example.likelion.service.mail.MailService;
import org.example.likelion.service.sms.InfobipService;
import org.example.likelion.utils.OtpGeneratorUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpGeneratorUtils otpGenerator;
    private final InfobipService infobipService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Boolean sendOtpVisSms() {
        UserResponse userDetails = authenticationService.getCurrentUserInfo().orElse(null);
        Optional<User> userInfo = userRepository.findByUsername(userDetails.getUsername());

        UserResponse userResponse = IUserMapper.INSTANCE.toDtoResponse(userInfo.orElse(null));
        boolean rs;
        Integer otpValue = otpGenerator.generateOTP(userResponse.getUsername());
        if (otpValue == -1) {
            return false;
        }
        HttpStatusCode returnStatus = infobipService.sendOtpCode("+84" + userResponse.getPhoneNumber(), InfoMessage.OTP_MESSAGE + otpValue);
        rs = returnStatus.is2xxSuccessful();
        return rs;

    }

    @SneakyThrows
    @Override
    public Boolean sendOtpVisEmail() {
        UserResponse userDetails = authenticationService.getCurrentUserInfo().orElse(null);

        Optional<User> userInfo = userRepository.findByUsername(userDetails.getUsername());

        UserResponse userResponse = IUserMapper.INSTANCE.toDtoResponse(userInfo.orElse(null));
        boolean rs;
        Integer otpValue = otpGenerator.generateOTP(userResponse.getUsername());
        if (otpValue == -1) {
            return false;
        }
        mailService.sendOtpCode(userResponse.getEmail(), otpValue.toString(), userResponse.getUsername());
        return true;

    }

    @Override
    public Boolean validateOTP(Integer otpNumber) {
        UserResponse userDetails = authenticationService.getCurrentUserInfo().orElse(null);

        Integer cacheOTP = otpGenerator.getOPTByKey(userDetails.getUsername());
        if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
            otpGenerator.clearOTPFromCache(userDetails.getUsername());
            User userInfo = userRepository.findUserByUsername(userDetails.getUsername());
            userInfo.setVerify(true);
            userRepository.save(userInfo);
            return true;
        }
        return false;
    }
}
