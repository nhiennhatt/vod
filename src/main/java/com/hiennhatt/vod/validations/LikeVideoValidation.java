package com.hiennhatt.vod.validations;

import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Setter
public class LikeVideoValidation {
    @UUID
    private String videoId;

    public java.util.UUID getVideoId() {
        return java.util.UUID.fromString(videoId);
    }
}
