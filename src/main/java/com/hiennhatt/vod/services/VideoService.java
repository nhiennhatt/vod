package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoDetailProjection;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import com.hiennhatt.vod.validations.UpdateVideoThumbnailValidation;
import com.hiennhatt.vod.validations.UpdateVideoValidation;
import com.hiennhatt.vod.validations.UploadVideoValidation;

import java.util.UUID;

public interface VideoService {
    Video uploadVideo(UploadVideoValidation uploadVideoValidation, User user);
    VideoOverviewProjection getVideoOverview(String uuid);
    VideoDetailProjection getVideo(String uuid);
    void updateVideo(UUID uid, UpdateVideoValidation video, User user);
    void updateVideoThumbnail(UUID uid, UpdateVideoThumbnailValidation thumbnail, User user);
    void deleteVideo(String uuid, User user);
}
