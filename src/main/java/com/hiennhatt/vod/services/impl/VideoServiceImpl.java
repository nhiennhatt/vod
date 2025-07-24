package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.utils.ffmpeg.FFmpegUtils;
import com.hiennhatt.vod.utils.ffmpeg.MultimediaInform;
import com.hiennhatt.vod.validations.UploadVideoValidation;
import jakarta.annotation.PostConstruct;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {
    private Path tempDirPath;
    private Path publicDirPath;
    private Path videoDirPath;

    @Autowired
    private VideoRepository videoRepository;

    @PostConstruct
    public void init(
        @Value("${tempDir}") String tempDir,
        @Value("${publicDir}") String publicDir,
        @Value("${videoDir}") String videoDir
    ) {
        tempDirPath = Path.of(tempDir);
        publicDirPath = Path.of(publicDir);
        videoDirPath = Path.of(videoDir);

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
        String uuid = UUID.randomUUID().toString();

        try {
            Path tempVideoPath = tempDirPath.resolve(uuid + "_temp");
            Path thumbnailPath = publicDirPath.resolve(uuid + getExtension(uploadVideoBody.getThumbnail().getContentType()));
            Path videoItemDirPath = videoDirPath.resolve(uuid);
            Files.createDirectories(videoItemDirPath);
            Path videoPath = videoItemDirPath.resolve("manifest.mpd");

            uploadVideoBody.getThumbnail().transferTo(thumbnailPath);
            uploadVideoBody.getVideo().transferTo(tempVideoPath);

            MultimediaInform videoStreamInform = FFmpegUtils.getStreamInform("v:0", tempVideoPath.toString());
            if (videoStreamInform == null || videoStreamInform.getStreams().isEmpty())
                throw new Exception("Invalid video file");

            MultimediaInform audioStreamInform = FFmpegUtils.getStreamInform("a:0", tempVideoPath.toString());
            FFmpegUtils.generateMpd(videoPath.toString(), tempVideoPath.toString(), videoStreamInform.getStreams().get(0), audioStreamInform != null && !audioStreamInform.getStreams().isEmpty() ? audioStreamInform.getStreams().get(0) : null);

            Video video = generateVideoInstance(uploadVideoBody, user, uuid);
            videoRepository.save(video);
            tempVideoPath.toFile().delete();
            return video;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Video generateVideoInstance(UploadVideoValidation uploadVideoBody, User user, String uuid) {
        Video video = new Video();
        video.setUid(UUID.fromString(uuid));
        video.setUser(user);
        video.setTitle(uploadVideoBody.getTitle());
        video.setDescription(uploadVideoBody.getDescription());
        video.setPrivacy(uploadVideoBody.getPrivacyEnum());
        video.setStatus(Video.Status.PROCESSING);
        video.setThumbnail(uuid + "." + Objects.requireNonNull(uploadVideoBody.getThumbnail().getOriginalFilename()).substring(uploadVideoBody.getThumbnail().getOriginalFilename().lastIndexOf(".") + 1));
        video.setFileName(uuid + "." + Objects.requireNonNull(uploadVideoBody.getVideo().getOriginalFilename()).substring(uploadVideoBody.getVideo().getOriginalFilename().lastIndexOf(".") + 1));
        return video;
    }

    private String getExtension(String contentType) {
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        try {
            MimeType mimeType = mimeTypes.forName(contentType);
            return mimeType.getExtension();
        } catch (MimeTypeException e) {
            return "";
        }
    }
}
