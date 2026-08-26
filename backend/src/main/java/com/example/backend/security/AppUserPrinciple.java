package com.example.backend.security;

import com.example.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AppUserPrinciple {
    public  final User user;
    public final Map<String , Object> attribute;

    public UUID getId(){
        return user.getId();
    }
    @Override
    public Map<String,Object> getAttribute(){
        return attribute;
    }
    @Override
    public Collection<?extends GrantedAuthority> getAuthorities(){
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
    @Override
    public String getName(){
        return user.getId().toString();
    }
    @Override
    public String getName(){
        return user.getId().toString();
    }
}
