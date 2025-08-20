package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.models.VideoCategory;
import com.hiennhatt.vod.repositories.CategoryRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.VideoCategoryRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.repositories.projections.IdentifiableVideoProjection;
import com.hiennhatt.vod.services.VideoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoCategoryServiceImpl implements VideoCategoryService {
    @Autowired
    private VideoCategoryRepository videoCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    @Transactional
    public void addCategoriesToVideo(List<String> categories, String videoIdStr, User user) {
        UUID videoId = UUID.fromString(videoIdStr);
        System.out.println(videoIdStr);
        IdentifiableVideoProjection videoProjection = videoRepository.findIdentifiableVideoProjectionByUid(videoId);
        if (videoProjection == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");

        if (!videoProjection.getUser().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to add categories to the video");

        if (videoProjection.getStatus().equals(Video.Status.INACTIVE))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to add categories to the video");

        Video video = videoProjection.toVideo();
        List<VideoCategory> videoCategories = categories.stream().map(c -> {
            Category category = categoryRepository.findCategoryBySlug(c);
            if (category == null) return null;
            VideoCategory videoCategory = new VideoCategory();
            videoCategory.setVideo(video);
            videoCategory.setCategory(category);
            return videoCategory;
        }).filter(Objects::nonNull).toList();

        if (videoCategories.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video category not found");

        videoCategoryRepository.saveAll(videoCategories);
    }
}
