package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoDetail;
import com.hiennhatt.vod.repositories.projections.VideoOverview;
import com.hiennhatt.vod.validations.UpdateVideoThumbnailValidation;
import com.hiennhatt.vod.validations.UpdateVideoValidation;
import com.hiennhatt.vod.validations.UploadVideoValidation;

import java.util.UUID;

public interface VideoService {
    Video uploadVideo(UploadVideoValidation uploadVideoValidation, User user);
    VideoOverview getVideoOverview(String uuid);
    VideoDetail getVideo(String uuid);
    void updateVideo(UUID uid, UpdateVideoValidation video, User user);
    void updateVideoThumbnail(UUID uid, UpdateVideoThumbnailValidation thumbnail, User user);
    void deleteVideo(String uuid, User user);
}
