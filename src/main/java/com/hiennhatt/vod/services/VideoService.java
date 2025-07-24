package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.validations.UploadVideoValidation;

public interface VideoService {
    Video uploadVideo(UploadVideoValidation uploadVideoValidation, User user);
}
