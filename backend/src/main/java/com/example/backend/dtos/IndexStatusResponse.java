package com.example.backend.dtos;

import com.example.backend.entity.IndexStatus;
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
public class IndexStatusResponse {
    private UUID repositoryId;
    private IndexStatus indexStatus;
    private int filesTotal;
    private int fileProcessed;
    private int chunkCount;
    private Instant indexAt;
    private String errorMessage;
}
