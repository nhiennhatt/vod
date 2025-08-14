package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.LikeDTO;
import com.hiennhatt.vod.models.Like;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.LikeRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            Video video = videoRepository.findVideoByUid(videoId);
            if (video == null || video.getStatus() == Video.Status.INACTIVE)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");

            if (video.getPrivacy() == Video.Privacy.PRIVATE && !video.getUser().getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to like private video");

            if (likeRepository.existsLikeByVideoUidAndUser(videoId, user))
                return;

            Like like = new Like();
            like.setVideo(video);
            like.setUser(user);
            like.setUid(UUID.randomUUID());

            likeRepository.save(like);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid video id");
        }
    }

    @Override
    public void unlikeVideo(UUID videoId, User user) {
        try {
            Video video = videoRepository.findVideoByUid(videoId);

            if (video == null || video.getStatus() == Video.Status.INACTIVE)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");

            Like like = likeRepository.findLikeByVideoUidAndUser(videoId, user);
            if (like == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not liked by user");

            likeRepository.delete(like);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid video id");
        }
    }

    @Override
    public boolean isVideoLiked(UUID videoId, User user) {
        try {
            Video video = videoRepository.findVideoByUid(videoId);
            if (video == null || video.getStatus() == Video.Status.INACTIVE)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
            if (video.getPrivacy() == Video.Privacy.PRIVATE && !video.getUser().getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to like private video");

            return likeRepository.existsLikeByVideoUidAndUser(videoId, user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid video id");
        }
    }

    @Override
    public long getLikesCount(UUID videoId) {
        try {
            Video video = videoRepository.findVideoByUid(videoId);
            if (video == null || video.getStatus() == Video.Status.INACTIVE)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");

            return likeRepository.countLikesByVideo_Uid(videoId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid video id");
        }
    }

    @Override
    public List<LikeDTO> getVideosLikedByUser(User user) {
        return likeRepository.findLikesOfUser(user);
    }
}
