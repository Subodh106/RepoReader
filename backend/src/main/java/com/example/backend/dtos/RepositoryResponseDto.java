package com.example.backend.dtos;

import com.example.backend.entity.IndexStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RepositoryResponseDto {
    private UUID id;
    private Long githubRepoId;
    private String owner;
    private String fullName;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
    private String language;
    private String htmlUrl;
    private String description;
    private IndexStatus indexStatus;
    private Instant indexedAt;
    private int chunkCount;
    private int filesTotal;
    private int filesProcessed;
    private String errorMessage;
}
