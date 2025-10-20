package com.hiennhatt.vod.dtos;

import lombok.Getter;

@Getter
public class CountSubscribersDTO {
    private long count;
    public CountSubscribersDTO(long count) {
        this.count = count;
    }
}
