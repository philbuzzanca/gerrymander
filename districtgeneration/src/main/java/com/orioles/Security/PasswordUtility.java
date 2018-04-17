package com.orioles.Security;

import com.orioles.constants.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtility {

  public static String encode(String rawPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(rawPassword);
  }

  public static boolean matches(String rawPassword, String encodedPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(rawPassword, encodedPassword);
  }
}
