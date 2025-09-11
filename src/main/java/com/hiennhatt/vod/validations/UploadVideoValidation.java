package com.hiennhatt.vod.validations;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.validator.IsImage;
import com.hiennhatt.vod.validator.IsVideo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UploadVideoValidation {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Pattern(regexp = "^PUBLIC|PRIVATE|LIMITED$")
    private String privacy;

    private List<String> categories;

    @NotNull
    @IsImage
    private MultipartFile thumbnail;

    @NotNull
    @IsVideo
    private MultipartFile video;

    public Video.Privacy getPrivacyEnum() {
        return Video.Privacy.valueOf(privacy);
    }
}
