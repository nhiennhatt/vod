package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.GainTokenDTO;
import com.hiennhatt.vod.services.TokenService;
import com.hiennhatt.vod.validations.GainTokenValidation;
import com.hiennhatt.vod.validations.RefreshTokenValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    @Operation(summary = "Generate token", description = "Generate token for user", responses = {@ApiResponse(content = {@Content(schema = @Schema(implementation = GainTokenDTO.class))})})
    public GainTokenDTO getToken(@RequestBody @Valid GainTokenValidation body) {
        return this.tokenService.gainToken(body.getUsername(), body.getPassword());
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refresh token for user", responses = {@ApiResponse(content = {@Content(schema = @Schema(implementation = GainTokenDTO.class))})})
    public GainTokenDTO refreshToken(@RequestBody @Valid RefreshTokenValidation body) {
        return this.tokenService.refreshToken(body.getToken());
    }
}
