package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.nimbusds.jose.JWEDecrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public User upsertFromGithub(Map<String , Object> attribute, String accessToken, String scopes) throws OAuth2AuthenticationException {
    Long githubId = (Long) attribute.get("id");
    String login = String.valueOf(attribute.get("login"));
    String name = attribute.get("name") !=null ? String.valueOf(attribute.get("name")):login;
    String avatarUrl = attribute.get("avatar_url")!=null ? String.valueOf(attribute.get("avatar_url")):null;
    User user = userRepository.findByGithubId(githubId).orElseGet(User::new);
    user.setGithubId(githubId);
    user.setGithubUsername(login);
    user.setUsername(name);
    user.setAvatarUrl(avatarUrl);
    user.setAccessToken(accessToken);
    user.setTokenScope(scopes);
    return user;
    }

    public User requiredById(UUID id){
        return userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
    }


}
