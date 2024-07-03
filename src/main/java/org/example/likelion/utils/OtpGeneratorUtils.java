package org.example.likelion.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OtpGeneratorUtils {
    private static final Integer EXPIRE_MIN = 5;
    private final LoadingCache<String, Integer> otpCache;

    public OtpGeneratorUtils() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public Integer generateOTP(String key) {
        SecureRandom random = new SecureRandom();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);
        return OTP;
    }

    public Integer getOPTByKey(String key) {
        return otpCache.getIfPresent(key);
    }

    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
}
