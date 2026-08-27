package com.example.backend.security;

import com.example.backend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
public class AppUserPrincipal implements OAuth2User {
    private final User user;
    private final Map<String , Object> attributes;

    public AppUserPrincipal(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public UUID getID(){
        assert user != null;
        return user.getId();
    }
    @Override
    public @NonNull Map<String , Object> getAttributes(){
        return attributes;
    }
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities(){
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
    @Override
    public @NonNull String getName(){
        assert user != null;
        return user.getId().toString();
    }

}
