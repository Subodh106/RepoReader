package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private UUID id;
    private Long githubId;
    private String githubUsername;
    private String providerId;
    private String displayName;
    private String avatarUrl;

}
