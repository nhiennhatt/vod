package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.Video;

import java.time.Instant;
import java.util.UUID;

public interface VideoOverviewProjection {
    UUID getUid();
    String getTitle();
    String getThumbnail();

    Video.Privacy getPrivacy();
    Video.Status getStatus();
    UserOverviewProjection getUser();

    Instant getCreatedOn();
}
