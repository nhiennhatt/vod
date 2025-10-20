package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.LikeDTO;
import com.hiennhatt.vod.models.Like;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.LikeRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.LikeService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public void likeVideo(UUID videoId, User user) {
        Video video = videoRepository.findVideoByUid(videoId);
        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        if (video.getPrivacy() == Video.Privacy.PRIVATE && !video.getUser().getId().equals(user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        if (likeRepository.existsLikeByVideoUidAndUser(videoId, user))
            return;

        Like like = new Like();
        like.setVideo(video);
        like.setUser(user);
        like.setUid(UUID.randomUUID());

        likeRepository.save(like);
    }

    @Override
    public void unlikeVideo(UUID videoId, User user) {
        Video video = videoRepository.findVideoByUid(videoId);

        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        Like like = likeRepository.findLikeByVideoUidAndUser(videoId, user);
        if (like == null)
            throw new HTTPResponseStatusException("Video's not liked by user", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        likeRepository.delete(like);
    }

    @Override
    public boolean isVideoLiked(UUID videoId, User user) {
        Video video = videoRepository.findVideoByUid(videoId);
        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        if (video.getPrivacy() == Video.Privacy.PRIVATE && !video.getUser().getId().equals(user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        return likeRepository.existsLikeByVideoUidAndUser(videoId, user);
    }

    @Override
    public long getLikesCount(UUID videoId) {
        Video video = videoRepository.findVideoByUid(videoId);
        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        return likeRepository.countLikesByVideo_Uid(videoId);
    }

    @Override
    public List<LikeDTO> getVideosLikedByUser(User user) {
        return likeRepository.findLikesOfUser(user);
    }
}
