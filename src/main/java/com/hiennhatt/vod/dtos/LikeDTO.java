package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Like;
import lombok.Getter;

import java.time.Instant;

@Getter
public class LikeDTO {
    private final Instant createdOn;
    private final VideoOverviewDTO video;
    public LikeDTO(Like like) {
        createdOn = like.getCreatedOn();
        video = new VideoOverviewDTO(like.getVideo());
    }
}
