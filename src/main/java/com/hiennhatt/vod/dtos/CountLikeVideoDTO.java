package com.hiennhatt.vod.dtos;

import lombok.Getter;

public class CountLikeVideoDTO {
    @Getter
    private final long count;
    public CountLikeVideoDTO(long count) {
        this.count = count;
    }
}
