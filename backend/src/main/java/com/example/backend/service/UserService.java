package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static org.springframework.expression.common.ExpressionUtils.toLong;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;
    public final TextEncryptor textEncryptor;

    @Transactional
    public User requiredById(Long id){
        return userRepository.findByGithubId(id).orElseThrow(()->new IllegalArgumentException("User not foud"));
    }
    public String decryptAccessToken(User user){
        return textEncryptor.decrypt(user.getAccessToken());
    }

    public User upsertFromGithub(Map<String , Object> attribute, String accessToken, String scopes) throws OAuth2AuthenticationException {
    Long githubId = (Long) attribute.get("id");
    String login = String.valueOf(attribute.get("login"));
    String name = attribute.get("name") !=null ? String.valueOf(attribute.get("name")):login;
    String avatarUrl = attribute.get("avatar_url")!=null ? String.valueOf(attribute.get("avatar_url")):null;
    String encryptedToken = textEncryptor.encrypt(accessToken);
    User user = userRepository.findByGithubId(githubId).orElseGet(User::new);
    user.setGithubID(githubId);
    user.setGithubUsername(login);
    user.setUsername(name);
    user.setAvatarUrl(avatarUrl);
    user.setAccessToken(encryptedToken);
    user.setTokenScope(scopes);
    return user;
    }


}
