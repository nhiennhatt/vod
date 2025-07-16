package com.hiennhatt.vod.services;

import com.hiennhatt.vod.dtos.GainTokenDTO;

public interface TokenService {
    GainTokenDTO generateToken(String username, String password);
}
