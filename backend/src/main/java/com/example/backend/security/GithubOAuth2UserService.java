package com.example.backend.security;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GithubOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserService userService;
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public AppUserPrinciple loadUser(@NonNull OAuth2UserRequest oAuth2UserRequest)  throws OAuth2AuthenticationException{
        OAuth2User githubUser = defaultOAuth2UserService.loadUser(oAuth2UserRequest);
        String accessToken = oAuth2UserRequest.getAccessToken().toString();
        String scopes = String.join(",",oAuth2UserRequest.getAccessToken().getScopes());
        User user = userService.upsertFromGithub(Objects.requireNonNull(githubUser.getAttribute(githubUser.getName())),accessToken,scopes);
        return new AppUserPrinciple(user,githubUser.getAttribute(githubUser.getName()));

    }
}
