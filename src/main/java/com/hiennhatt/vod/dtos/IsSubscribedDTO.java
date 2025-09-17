package com.hiennhatt.vod.dtos;

import lombok.Getter;

@Getter
public class IsSubscribedDTO {
    private final boolean isSubscribed;
    public IsSubscribedDTO(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
}
