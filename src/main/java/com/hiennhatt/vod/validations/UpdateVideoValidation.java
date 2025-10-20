package com.hiennhatt.vod.validations;

import com.hiennhatt.vod.models.Video;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVideoValidation {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Pattern(regexp = "^PUBLIC|PRIVATE|LIMITED$")
    private String privacy;

    public Video.Privacy getPrivacy() {
        return Video.Privacy.valueOf(privacy);
    }
}
