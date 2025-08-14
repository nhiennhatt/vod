package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.Video;

import java.util.UUID;

public interface VideoDetailProjection {
    UUID getUid();
    String getTitle();
    String getDescription();
    Video.Status getStatus();
    Video.Privacy getPrivacy();
    String getFileName();
    String getThumbnail();
    UserOverviewProjection getUser();
}
