package com.chencye.designpatterns.patterns.common;

import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author chencye
 */
public final class KeyGenerator {

    private static final Random RANDOM = new SecureRandom();
    private static final String ALGORITHM = "MD5";

    public static String gen(String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            String text = salt + System.currentTimeMillis() + RANDOM.nextInt();
            messageDigest.update(text.getBytes());
            return Base64Utils.encodeToUrlSafeString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("KeyGenerator failed.", e);
        }
    }
}
