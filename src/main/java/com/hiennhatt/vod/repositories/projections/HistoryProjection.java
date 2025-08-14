package com.hiennhatt.vod.repositories.projections;

import java.time.Instant;
import java.util.UUID;

public interface HistoryProjection {
    UUID getUid();
    VideoOverviewProjection getVideo();
    Instant getCreatedOn();
}
