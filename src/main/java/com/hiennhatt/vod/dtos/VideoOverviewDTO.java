package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class VideoOverviewDTO {
    private UUID uid;
    private String title;
    private String thumbnail;
    private Video.Status status;
    private Video.Privacy privacy;
    private UserOverviewDTO user;
    private Instant createdOn;
    public VideoOverviewDTO(Video video) {
        uid = video.getUid();
        title = video.getTitle();
        status = video.getStatus();
        privacy = video.getPrivacy();
        thumbnail = video.getThumbnail();
        createdOn = video.getCreatedOn();
        user = new UserOverviewDTO(video.getUser());
    }
    public VideoOverviewDTO(VideoOverviewProjection videoOverview) {
        uid = videoOverview.getUid();
        title = videoOverview.getTitle();
        status = videoOverview.getStatus();
        privacy = videoOverview.getPrivacy();
        thumbnail = videoOverview.getThumbnail();
        createdOn = videoOverview.getCreatedOn();
        user = new UserOverviewDTO(videoOverview.getUser());
    }
}
