package com.hiennhatt.vod.dtos;

import lombok.Getter;

public class CountCommentDTO {
    @Getter
    private final long count;
    public CountCommentDTO(long count) {
        this.count = count;
    }
}
