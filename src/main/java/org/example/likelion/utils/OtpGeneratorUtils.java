package org.example.likelion.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;
@Component
public class OtpGeneratorUtils {
    private static final Integer EXPIRE_MIN = 5;
    private final LoadingCache<String, Integer> otpCache;

    public OtpGeneratorUtils() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public Integer generateOTP(String key) {
        Random random = new Random();
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
