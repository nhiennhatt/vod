package com.hiennhatt.vod.dtos;

import lombok.Getter;

@Getter
public class CountVideoDTO {
    private final long count;
    public CountVideoDTO(long count) {
        this.count = count;
    }
}
