package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveHistoryValidation {
    @NotBlank
    private String videoId;
}
