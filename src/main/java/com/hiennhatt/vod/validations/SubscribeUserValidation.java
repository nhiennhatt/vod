package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeUserValidation {
    @NotBlank
    private String targetUsername;
}
