package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.models.VideoCategory;
import com.hiennhatt.vod.repositories.CategoryRepository;
import com.hiennhatt.vod.repositories.VideoCategoryRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.repositories.projections.IdentifiableVideoProjection;
import com.hiennhatt.vod.services.VideoCategoryService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            throw new HTTPResponseStatusException("User not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);;

        if (!videoProjection.getUser().getId().equals(user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        if (videoProjection.getStatus().equals(Video.Status.INACTIVE))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

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
            throw new HTTPResponseStatusException("Categories not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);;

        videoCategoryRepository.saveAll(videoCategories);
    }

    @Override
    public List<Category> getVideoCategories(UUID uid) {
        Video video = videoRepository.findVideoByUid(uid);
        System.out.println(video);
        if (video == null) return List.of();
        return video.getCategories();
    }

    @Override
    public List<Video> getVideosByCategory(String slug, Pageable pageable) {
        Category category = categoryRepository.findCategoryBySlug(slug);
        if (category == null) return List.of();
        return videoCategoryRepository.findVideoCategoriesByCategory(category, pageable).stream().map(VideoCategory::getVideo).toList();
    }
}
