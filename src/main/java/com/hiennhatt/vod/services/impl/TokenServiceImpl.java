package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.GainTokenDTO;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import com.hiennhatt.vod.services.TokenService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import com.hiennhatt.vod.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtAccessTokenUtil;
    @Autowired
    private JWTUtils jwtRefreshTokenUtil;
    @Autowired
    private Environment env;

    @Override
    public GainTokenDTO generateToken(AuthorizationUserProjection user) {
        Instant now = Instant.now();
        JwtClaimsSet accessTokenClaimSet = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(now.plusSeconds(Integer.parseInt(env.getProperty("jwt.expiration-s.access", "0"))))
            .subject(user.getUsername())
            .claim("role", user.getRole().name())
            .build();

        JwtClaimsSet refreshTokenClaimSet = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(now.plusSeconds(Integer.parseInt(env.getProperty("jwt.expiration-s.refresh", "0"))))
            .subject(user.getUsername())
            .claim("role", user.getRole().name())
            .build();

        String accessToken = this.jwtAccessTokenUtil.jwtEncoder().encode(JwtEncoderParameters.from(accessTokenClaimSet)).getTokenValue();
        String refreshToken = this.jwtRefreshTokenUtil.jwtEncoder().encode(JwtEncoderParameters.from(refreshTokenClaimSet)).getTokenValue();

        return new GainTokenDTO(accessToken, refreshToken);
    }

    @Override
    public GainTokenDTO gainToken(String username, String password) {
        AuthorizationUserProjection user = userRepository.findAuthorizationUserByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword()))
            throw new HTTPResponseStatusException("Invalid username or password", "WRONG_USER_INFORM", HttpStatus.UNAUTHORIZED, null);
        return this.generateToken(user);
    }

    @Override
    public GainTokenDTO refreshToken(String refreshToken) {
        Jwt jwt = jwtRefreshTokenUtil.jwtDecoder().decode(refreshToken);
        String username = (String) jwt.getClaims().get("sub");
        AuthorizationUserProjection user = userRepository.findAuthorizationUserByUsername(username);
        if (user == null) throw new HTTPResponseStatusException("Invalid refresh token", "INVALID_TOKEN", HttpStatus.valueOf(401), null);
        return this.generateToken(user);
    }
}
