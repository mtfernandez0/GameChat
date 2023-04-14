package com.mati.gamechat.security.config.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordEncoderServiceImpl {
    public PasswordEncoder passwordEncoder(){
        return new DelegatingPasswordEncoder("bcrypt", passwordEncoderMap());
    }

    private Map<String, PasswordEncoder> passwordEncoderMap(){
        Map<String, PasswordEncoder> encoderMap = new HashMap<>();

        encoderMap.put("bcrypt", new BCryptPasswordEncoder());
        encoderMap.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoderMap.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoderMap.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());

        return encoderMap;
    }
}