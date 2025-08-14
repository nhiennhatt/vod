package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.UserOverviewProjection;
import lombok.Getter;

@Getter
public class UserOverviewDTO {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String avatar;
    public UserOverviewDTO(String username, String firstName, String lastName, String avatar) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public UserOverviewDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getUserInform().getFirstName();
        this.lastName = user.getUserInform().getLastName();
        this.avatar = user.getUserInform().getAvatar();
    }

    public UserOverviewDTO(UserOverviewProjection user) {
        this.username = user.getUsername();
        this.firstName = user.getUserInform().getFirstName();
        this.lastName = user.getUserInform().getLastName();
        this.avatar = user.getUserInform().getAvatar();
    }
}
