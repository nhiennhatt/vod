package com.hiennhatt.vod.configs;

import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("activeUserAccessManager")
public class ActiveUserAccessManager {
    public boolean isActiveUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getStatus() == User.Status.ACTIVE;
        }
        return false;
    }
}
