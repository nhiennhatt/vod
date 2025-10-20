package com.hiennhatt.vod.models;

import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails{
    private final AuthorizationUserProjection user;
    public CustomUserDetails(AuthorizationUserProjection authorizationUserProjection) {
        this.user = authorizationUserProjection;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public User.Status getStatus() {
        return user.getStatus();
    }

    public User getUser() {
        return user.toUser();
    }

    public AuthorizationUserProjection getAuthorizationUser() {
        return user;
    }
}
