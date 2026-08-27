package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "github_id" , unique = true , nullable = false , length = 200)
    private Long githubId;
    @Column(name="github_username" , length = 200)
    private String githubUsername;
    @Column(name="username" , nullable = false ,length = 200)
    private String Username;
    @Column(name="avatar_url" , length = 500)
    private String avatarUrl;
    @Column(name="access_token" , nullable = false , columnDefinition = "TEXT")
    private String accessToken;
    @Column(name="token_scope" , length = 250)
    private String tokenScope;
    @Column(name = "created_at" , updatable = false)
    private Instant createdAt;
    @PrePersist
        void onCreated(){
            if(createdAt == null){
                createdAt = Instant.now();
            }
    }
}
