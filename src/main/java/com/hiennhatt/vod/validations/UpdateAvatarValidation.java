package com.hiennhatt.vod.validations;

import com.hiennhatt.vod.validator.IsImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateAvatarValidation {
    @IsImage
    private MultipartFile avatar;
}
