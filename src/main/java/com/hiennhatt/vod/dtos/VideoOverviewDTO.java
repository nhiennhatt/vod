package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoOverview;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class VideoOverviewDTO {
    private UUID uid;
    private String title;
    private Video.Status status;
    private Video.Privacy privacy;
    private UserOverviewDTO user;
    public VideoOverviewDTO(Video video) {
        uid = video.getUid();
        title = video.getTitle();
        status = video.getStatus();
        privacy = video.getPrivacy();
        user = new UserOverviewDTO(video.getUser());
    }
    public VideoOverviewDTO(VideoOverviewProjection videoOverview) {
        uid = videoOverview.getUid();
        title = videoOverview.getTitle();
        status = videoOverview.getStatus();
        privacy = videoOverview.getPrivacy();
        user = new UserOverviewDTO(videoOverview.getUser());
    }
    public VideoOverviewDTO(){}
}
