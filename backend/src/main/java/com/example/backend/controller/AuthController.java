package com.example.backend.controller;

import com.example.backend.dtos.UserResponseDto;
import com.example.backend.entity.User;
import com.example.backend.security.AppUserPrincipal;
import com.example.backend.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final CurrentUser currentUser;
    @GetMapping("/login-url")
    public Map<String , String> loginUrl(){
        return Map.of("url","/oauth2/authorization/github");
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMethodName(){
        AppUserPrincipal principal = currentUser.require();
        User user = principal.getUser();
        return ResponseEntity.ok(new UserResponseDto(user.getId(),user.getGithubId(),user.getGithubUsername(),user.getUsername(),user.getAvatarUrl()));
    }
}
