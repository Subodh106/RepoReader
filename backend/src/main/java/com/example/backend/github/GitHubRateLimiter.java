package com.example.backend.github;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitHubRateLimiter {
    private final Long delay;
    public GitHubRateLimiter(@Value("${app.github.api-delay-ms:50}") long delay){
        this.delay = Math.max(0,delay);
    }

    public void pause(){
        if(delay<=0){
            return;
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException();
        }
    }
}
