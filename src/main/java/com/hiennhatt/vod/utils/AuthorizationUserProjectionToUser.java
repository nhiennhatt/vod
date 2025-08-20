package com.hiennhatt.vod.utils;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;

public class AuthorizationUserProjectionToUser {
    static public User toUser(AuthorizationUserProjection authorizationUserToUser) {
        User user = new User();
        user.setId(authorizationUserToUser.getId());
        user.setUsername(authorizationUserToUser.getUsername());
        user.setPassword(authorizationUserToUser.getPassword());
        user.setRole(authorizationUserToUser.getRole());
        user.setStatus(authorizationUserToUser.getStatus());
        return user;
    }
}
