package com.hiennhatt.vod.repositories.projections;

public interface SubscribeProjection {
    UserOverviewProjection getSourceUser();
    UserOverviewProjection getDestUser();
}
