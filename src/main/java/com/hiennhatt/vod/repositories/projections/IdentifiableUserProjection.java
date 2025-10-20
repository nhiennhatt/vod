package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.User;

public interface IdentifiableUserProjection {
    Integer getId();
    String getUsername();

    default User toUser() {
        User user = new User();
        user.setId(getId());
        user.setUsername(getUsername());
        return user;
    }
}
