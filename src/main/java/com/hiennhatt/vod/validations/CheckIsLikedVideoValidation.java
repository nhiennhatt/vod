package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class CheckIsLikedVideoValidation {
    @org.hibernate.validator.constraints.UUID
    @NotBlank
    private String videoId;

    public java.util.UUID getVideoId() {
        return java.util.UUID.fromString(videoId);
    }
}
