package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateEmailValidation {
    @Email
    @NotNull
    private String email;
}
