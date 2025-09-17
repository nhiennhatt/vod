package com.hiennhatt.vod.services;

import com.hiennhatt.vod.dtos.GainTokenDTO;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;

public interface TokenService {
    GainTokenDTO gainToken(String username, String password);
    GainTokenDTO refreshToken(String refreshToken);
    GainTokenDTO generateToken(AuthorizationUserProjection user);
}
