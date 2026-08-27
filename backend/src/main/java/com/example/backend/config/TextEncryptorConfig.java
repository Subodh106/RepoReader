package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.AesGcmBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

@Configuration
public class TextEncryptorConfig {
    @Bean
    public BytesEncryptor textEncryptor(
            @Value("${app.token-encryption-password}") String password,
            @Value("${app.token-encryption-salt}") String salt) {
        return AesGcmBytesEncryptor.withPassword(password, salt).build();
    }
}
