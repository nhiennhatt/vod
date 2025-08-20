package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.User;

public interface AuthorizationUserProjection {
    Integer getId();
    String getUsername();
    String getPassword();

    User.Role getRole();
    User.Status getStatus();

    default User toUser() {
        User user = new User();
        user.setId(getId());
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        user.setRole(getRole());
        user.setStatus(getStatus());
        return user;
    }
}
