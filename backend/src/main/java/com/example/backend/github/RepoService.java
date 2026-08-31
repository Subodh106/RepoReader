package com.example.backend.github;

import com.example.backend.dtos.IndexStatusResponse;
import com.example.backend.dtos.RepositoryResponseDto;
import com.example.backend.entity.Repository;
import com.example.backend.entity.User;
import com.example.backend.exceptions.NotFoundException;
import com.example.backend.repository.RepoRepository;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RepoService {

    private final RepoRepository repoRepository;
    private final UserService userService;
    private final GithubApiClient githubApiClient;

    private static Long toLong(Object value){
        if(value instanceof Number number){
            return number.longValue();
        }
        return Long.parseLong(String.valueOf(value));
    }

    @Transactional
    public RepositoryResponseDto syncAndListRepos(UUID userId){
        User user = userService.requiredById(userId);
        String token = user.getAccessToken();
        List<Map<String , Object>> remoteRepos = githubApiClient.ListUserRepos(token);
        List<Repository> saved = new ArrayList<>();
        for(Map<String,Object> remote : remoteRepos){
            Long githubRepoId = toLong(remote.get("id"));
            Repository repo = repoRepository.findByUserIdAndGithubRepoId(userId,githubRepoId).orElseGet(Repository::new);
            String fullName = String.valueOf(remote.get("full_name"));
            String[] parts = fullName.split("/",2);
            repo.setUserId(userId);
            repo.setGithubRepoId(githubRepoId);
            repo.setOwner(parts.length>0?parts[0]:String.valueOf(remote.get("owner")));
            repo.setName(parts.length>0?parts[1]:String.valueOf(remote.get("name")));
            repo.setFullName(fullName);
            repo.setPrivate(Boolean.TRUE.equals(remote.get("private")));
            repo.setDefaultBranch(remote.get("default_branch")!=null?String.valueOf(remote.get("default_branch")):"main");
            repo.setLanguage(remote.get("language")!=null?String.valueOf(remote.get("Language")):null);
            repo.setHtmlUrl(remote.get("html_url")!=null?String.valueOf(remote.get("html_url")):null);
            repo.setUpdatedAt(Instant.now());
            if(repo.getOwner()==null|| repo.getOwner().isBlank()){
                Object ownerObj = remote.get("owner");
                if(ownerObj instanceof Map<?,?> ownerMap && ownerMap.get("login")!=null){
                    repo.setOwner(String.valueOf(ownerMap.get("login")));
                }
                saved.add(repoRepository.save(repo));
            }
        }
        return (RepositoryResponseDto) saved.stream().sorted((a, b)->a.getFullName().compareToIgnoreCase(b.getFullName())).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RepositoryResponseDto> listStored(UUID userId){
        return repoRepository.findUserIdOrderByFullNameAsc(userId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Repository requiredOwned(UUID repoId,UUID userId){
        return repoRepository.findIdAndUserId(repoId,userId).orElseThrow(()->new NotFoundException("Repository not found"));
    }

    @Transactional(readOnly = true)
    public IndexStatusResponse status(UUID repoId , UUID userId){
        Repository repo = requiredOwned(repoId,userId);
        return new IndexStatusResponse(
                repo.getId(),
                repo.getIndexStatus(),
                repo.getFileTotal(),
                repo.getFileTotal(),
                repo.getChunkCount(),
                repo.getIndexedAt(),
                repo.getErrorMessage()
        );
    }

    public RepositoryResponseDto toResponse(Repository repo){
        return new RepositoryResponseDto(
                repo.getId(),
                repo.getGithubRepoId(),
                repo.getOwner(),
                repo.getFullName(),
                repo.isPrivate(),
                repo.getLanguage(),
                repo.getHtmlUrl(),
                repo.getDescription(),
                repo.getIndexStatus(),
                repo.getIndexedAt(),
                repo.getChunkCount(),
                repo.getFileTotal(),
                repo.getChunkCount(),
                repo.getErrorMessage()
        );
    }
}
