package com.hiennhatt.vod.dtos;

import lombok.Getter;

public class IsLikedVideoDTO {
    @Getter
    boolean isLiked;
    public IsLikedVideoDTO(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
