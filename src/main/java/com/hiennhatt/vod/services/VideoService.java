package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoOverview;
import com.hiennhatt.vod.validations.UploadVideoValidation;

public interface VideoService {
    Video uploadVideo(UploadVideoValidation uploadVideoValidation, User user);
    VideoOverview getVideoOverview(String uuid);
}
