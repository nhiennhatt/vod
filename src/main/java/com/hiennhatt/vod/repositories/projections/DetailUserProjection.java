package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.User;

public interface DetailUserProjection {
    String getUsername();
    String getEmail();
    User.Status getStatus();
    User.Role getRole();
    SelfUserInformProjection getUserInform();
}
