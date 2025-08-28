package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.models.VideoCategory;
import com.hiennhatt.vod.repositories.CategoryRepository;
import com.hiennhatt.vod.repositories.VideoCategoryRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.repositories.projections.VideoDetailProjection;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import com.hiennhatt.vod.utils.StoreUtils;
import com.hiennhatt.vod.utils.ffmpeg.FFmpegUtils;
import com.hiennhatt.vod.utils.ffmpeg.MultimediaInform;
import com.hiennhatt.vod.validations.UpdateVideoThumbnailValidation;
import com.hiennhatt.vod.validations.UpdateVideoValidation;
import com.hiennhatt.vod.validations.UploadVideoValidation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {
    private final Path tempDirPath;
    private final Path publicDirPath;
    private final Path videoDirPath;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VideoCategoryRepository videoCategoryRepository;

    public VideoServiceImpl(@Autowired Environment env) {
        tempDirPath = Path.of(env.getProperty("uploadedDir", "classpath:uploadDir/")).resolve("temp/");
        publicDirPath = Path.of(env.getProperty("uploadedDir", "classpath:uploadDir/")).resolve("public/");
        videoDirPath = publicDirPath.resolve("video/");

        try {
            Files.createDirectories(publicDirPath);
            Files.createDirectories(tempDirPath);
            Files.createDirectories(videoDirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Video uploadVideo(UploadVideoValidation uploadVideoBody, User user) {
        String uid = UUID.randomUUID().toString();
        String thumbnailUid = StoreUtils.generateUid();

        try {
            Path imagePath = StoreUtils.save(publicDirPath, thumbnailUid, uploadVideoBody.getThumbnail());
            Path tempVideoPath = StoreUtils.saveTemp(tempDirPath, uid, uploadVideoBody.getVideo());

            Path videoItemDirPath = videoDirPath.resolve(uid);
            Files.createDirectories(videoItemDirPath);
            Path videoPath = videoItemDirPath.resolve("manifest.mpd");

            MultimediaInform videoStreamInform = FFmpegUtils.getStreamInform("v:0", tempVideoPath.toString());
            if (videoStreamInform == null || videoStreamInform.getStreams().isEmpty())
                throw new Exception("Invalid video file");

            MultimediaInform audioStreamInform = FFmpegUtils.getStreamInform("a:0", tempVideoPath.toString());
            FFmpegUtils.generateMpd(videoPath.toString(), tempVideoPath.toString(), videoStreamInform.getStreams().get(0), audioStreamInform != null && !audioStreamInform.getStreams().isEmpty() ? audioStreamInform.getStreams().get(0) : null);

            Video video = generateVideoInstance(uploadVideoBody, user, uid, imagePath.getFileName().toString());
            videoRepository.save(video);
            List<VideoCategory> categories = uploadVideoBody.getCategories().stream().map(item -> {
                Category category = categoryRepository.findCategoryBySlug(item);
                if (category == null) return null;

                VideoCategory videoCategory = new VideoCategory();
                videoCategory.setVideo(video);
                videoCategory.setCategory(category);
                return videoCategory;
            }).filter(Objects::nonNull).toList();
            videoCategoryRepository.saveAll(categories);
            tempVideoPath.toFile().delete();
            return video;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload video");
        }
    }

    public VideoOverviewProjection getVideoOverview(String uuid) {
        VideoOverviewProjection video = videoRepository.getVideoOverview(UUID.fromString(uuid));
        if (video == null)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        return video;
    }

    private Video generateVideoInstance(UploadVideoValidation uploadVideoBody, User user, String uid, String imagePath) {
        Video video = new Video();
        video.setUid(UUID.fromString(uid));
        video.setUser(user);
        video.setTitle(uploadVideoBody.getTitle());
        video.setDescription(uploadVideoBody.getDescription());
        video.setPrivacy(uploadVideoBody.getPrivacyEnum());
        video.setStatus(Video.Status.PROCESSING);
        video.setThumbnail(imagePath);
        video.setFileName(uid);
        return video;
    }

    public VideoDetailProjection getVideo(String uuid) {
        VideoDetailProjection video = videoRepository.getVideoDetail(UUID.fromString(uuid));
        if (video == null)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        return video;
    }

    @Transactional
    public void updateVideo(UUID uid, UpdateVideoValidation video, User user) {
        this.videoRepository.updateVideo(uid, video.getTitle(), video.getDescription(), video.getPrivacy(), user.getId());
    }

    @Transactional
    public void updateVideoThumbnail(UUID uid, UpdateVideoThumbnailValidation thumbnail, User user) {
        Video video = this.videoRepository.getVideoByUid(uid);
        if (video == null)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        String oldThumbnail = video.getThumbnail();
        if (!Objects.equals(video.getUser().getId(), user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        try {
            Path imagePath = StoreUtils.save(publicDirPath, UUID.randomUUID().toString().replace("-", ""), thumbnail.getThumbnail());
            video.setThumbnail(imagePath.getFileName().toString());
            videoRepository.save(video);
            publicDirPath.resolve(oldThumbnail).toFile().delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update video thumbnail");
        }
    }

    @Transactional
    public void deleteVideo(String uuid, User user) {
        Video video = this.videoRepository.getVideoByUid(UUID.fromString(uuid));
        if (video == null)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        if (!Objects.equals(video.getUser().getId(), user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);
        int result = videoRepository.deleteVideoById(video.getId());
        if (result <= 0)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete video thumbnail");

        publicDirPath.resolve(video.getThumbnail()).toFile().delete();
        try {
            FileUtils.deleteDirectory(videoDirPath.resolve(uuid).toFile());
        } catch (IOException ignored) {
        }
    }

    @Override
    public List<VideoOverviewProjection> findVideoByKeyword(String keyword) {
        return videoRepository.findVideoOverviewProjectionsByTitleLikeOrDescriptionLikeAndStatusAndPrivacy(keyword, keyword, Video.Status.ACTIVE, Video.Privacy.PUBLIC);
    }
}
