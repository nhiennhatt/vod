package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Video;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VideoOverviewDTO {
    private final UUID uid;
    private final String title;
    private final Video.Status status;
    private final Video.Privacy privacy;
    private final UserOverviewDTO user;
    public VideoOverviewDTO(Video video) {
        uid = video.getUid();
        title = video.getTitle();
        status = video.getStatus();
        privacy = video.getPrivacy();
        user = new UserOverviewDTO(video.getUser());
    }
}
