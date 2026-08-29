package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="repositories",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "github_repo_id"}))
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "userId" , nullable = false)
    private UUID userId;
    @Column(name = "github_repo_id" , nullable = false)
    private Long githubRepoId;
    @Column(nullable = false , length = 100)
    private String owner;
    @Column(nullable = false ,  length = 100)
    private String name;
    @Column(name = "full_name",nullable = false,length = 300)
    private String fullName;
    @Column(name = "is_private",nullable = false , length = 100)
    private boolean isPrivate;
    @Column(name="default_branch" , nullable = false,length = 100)
    private String defaultBranch;
    @Column(length = 100)
    private String language;
    @Column(name = "html_url" ,length = 500)
    private String htmlUrl;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "index_status",nullable = false , length = 20)
    private IndexStatus indexStatus = IndexStatus.PENDING;
    @Column(name = "index_at")
    private Instant indexedAt;
    @Column(name = "chunk_count",nullable = false)
    private int chunkCount =0;
    @Column(name="file_total" , nullable = false)
    private int fileTotal =0;
    @Column(name = "error_message" , columnDefinition = "TEXT")
    private String errorMessage;
    @Column(name = "create_at",nullable = false , updatable = false)
    private Instant createdAt;
    @Column(name = "update_at" , nullable = false)
    private Instant updatedAt;
    @PrePersist
        void onCreate(){
        Instant now = Instant.now();
        if(createdAt==null){
            createdAt=now;
        }
        updatedAt = now;
        if(indexStatus == null){
            indexStatus = IndexStatus.PENDING;
        }
    }
}
