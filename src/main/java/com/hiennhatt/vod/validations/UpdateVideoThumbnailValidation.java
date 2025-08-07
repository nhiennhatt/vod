package com.hiennhatt.vod.validations;

import com.hiennhatt.vod.validator.IsImage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateVideoThumbnailValidation {
    @NotNull
    @IsImage
    private MultipartFile thumbnail;
}
