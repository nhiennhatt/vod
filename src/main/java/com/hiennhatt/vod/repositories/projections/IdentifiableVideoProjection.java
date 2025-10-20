package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;

import java.util.UUID;

public interface IdentifiableVideoProjection {
    Integer getId();
    UUID getUid();

    Video.Status getStatus();
    IdentifiableUser getUser();

    interface IdentifiableUser {
        Integer getId();
        String getUsername();
    }

    default Video toVideo() {
        Video video = new Video();
        video.setId(getId());
        video.setUid(getUid());
        video.setStatus(getStatus());
        User user = new User();
        user.setId(getUser().getId());
        user.setUsername(getUser().getUsername());
        video.setUser(user);
        return video;
    }
}
