package com.hiennhatt.vod.services;

import com.hiennhatt.vod.dtos.GainTokenDTO;

public interface TokenService {
    GainTokenDTO gainToken(String username, String password);
    GainTokenDTO refreshToken(String refreshToken);
}
