package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;
    public final TextEncryptor textEncryptor;

    @Transactional
    public User requiredById(UUID id){
        return userRepository.findByGithubId(id).orElseThrow(()->new IllegalArgumentException("User not foud"));
    }
    public String decryptAccessToken(User user){
        return textEncryptor.decrypt(user.getAccessToken());
    }

}
