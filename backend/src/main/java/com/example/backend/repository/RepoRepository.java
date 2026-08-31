package com.example.backend.repository;

import com.example.backend.entity.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepoRepository extends JpaRepository<Repository, UUID> {
    List<Repository> findUserIdOrderByFullNameAsc(UUID userID);

    Optional<Repository> findIdAndUserId(UUID repoId , UUID userId);

    Optional<Repository> findByUserIdAndGithubRepoId(UUID userId , Long githubRepoId);
}

