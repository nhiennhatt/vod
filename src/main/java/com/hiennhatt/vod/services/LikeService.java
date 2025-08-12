package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;

import java.util.UUID;

public interface LikeService {
    void likeVideo(UUID videoId, User user);
    void unlikeVideo(UUID videoId, User user);
    boolean isVideoLiked(UUID videoId, User user);
    long getLikesCount(UUID videoId, User user);
}
