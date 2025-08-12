package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class VideoOverview {
    private UUID uid;

    private String title;

    private Video.Privacy privacy;

    private Video.Status status;

    private String thumbnail;

    private UserOverviewDTO user;
}
