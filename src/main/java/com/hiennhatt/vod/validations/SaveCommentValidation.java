package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class SaveCommentValidation {
    @NotBlank
    @UUID
    private String videoId;

    @NotBlank
    private String comment;
}
