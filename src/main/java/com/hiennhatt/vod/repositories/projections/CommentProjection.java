package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.Comment;

import java.time.Instant;
import java.util.UUID;

public interface CommentProjection {
    UUID getUid();
    UserOverviewProjection getUser();
    String getContent();
    Comment.Status  getStatus();
    Instant getCreatedOn();
}
