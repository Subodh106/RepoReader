package com.example.backend.controller;

import com.example.backend.dtos.IndexStatusResponse;
import com.example.backend.dtos.RepositoryResponseDto;
import com.example.backend.github.RepoService;
import com.example.backend.repository.RepoRepository;
import com.example.backend.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/repos")
@RequiredArgsConstructor
public class RepoController {

    private final CurrentUser currentUser;
    private final RepoService repoService;

    @GetMapping
    public List<RepositoryResponseDto> list(@RequestParam(name = "refresh" , defaultValue = "true")boolean refresh){
        UUID userId = currentUser.require().getID();
        if(refresh){
            return Collections.singletonList(repoService.syncAndListRepos(userId));
        }
        return repoService.listStored(userId);
    }

    @GetMapping
    public RepositoryResponseDto get(@PathVariable UUID id){
        UUID userId = currentUser.require().getID();
        return repoService.toResponse(repoService.requiredOwned(id,userId));
    }

    @GetMapping("/{id}/status")
    public IndexStatusResponse status (@PathVariable UUID id){
        UUID userID = currentUser.require().getID();
        return repoService.status(id,userID);
    }
}
