package com.hiennhatt.vod.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GainTokenDTO {
    private String accessToken;
    private String refreshToken;
}
