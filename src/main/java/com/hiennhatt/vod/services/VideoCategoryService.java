package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface VideoCategoryService {
    void addCategoriesToVideo(List<String> categories, String videoId, User user);
    List<Category> getVideoCategories(UUID uid);
    List<Video> getVideosByCategory(String categorySlug, Pageable pageable);
}
