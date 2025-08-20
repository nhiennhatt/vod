package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.User;

public interface AuthorizationUserProjection {
    Integer getId();
    String getUsername();
    String getPassword();

    User.Role getRole();
    User.Status getStatus();
}
