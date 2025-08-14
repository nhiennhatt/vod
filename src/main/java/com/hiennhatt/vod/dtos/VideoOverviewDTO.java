package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Video;
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
    public VideoOverviewDTO(){}
}
