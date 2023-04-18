package com.cody.roughcode.security.auth;

import com.cody.roughcode.user.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDetailsCustom implements OAuth2User {
    private final Users user;
    private Map<String, Object> attributes;

    public UserDetailsCustom(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public UserDetailsCustom(Users user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for(String role: user.getRoles()){
            list.add(new SimpleGrantedAuthority(role));
        }
        return list;
    }

}

