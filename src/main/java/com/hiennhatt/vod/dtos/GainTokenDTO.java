package com.hiennhatt.vod.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GainTokenDTO {
    private String accessToken;
    private String refreshToken;
}
