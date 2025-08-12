package com.hiennhatt.vod.repositories.projections;

import com.hiennhatt.vod.models.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class VideoDetail {
    private UUID uid;
    private String title;
    private String description;
    private Video.Status status;
    private Video.Privacy privacy;
    private String fileName;
    private String thumbnail;
    private UserOverviewDTO user;
}
