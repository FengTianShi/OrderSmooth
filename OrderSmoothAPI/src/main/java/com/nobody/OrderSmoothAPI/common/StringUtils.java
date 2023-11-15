package com.nobody.OrderSmoothAPI.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class StringUtils {

  public static String encryptPassword(String password) {
    try {
      return Base64
        .getEncoder()
        .encodeToString(
          MessageDigest.getInstance("SHA-256").digest(password.getBytes())
        );
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return password;
    }
  }

  public static String generateOTP(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("length must be greater than 0");
    }

    Random random = new Random();
    StringBuilder codeBuilder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      int digit = random.nextInt(10);
      codeBuilder.append(digit);
    }

    return codeBuilder.toString();
  }

  public static String generateUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
