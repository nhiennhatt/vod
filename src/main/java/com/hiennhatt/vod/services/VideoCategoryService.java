package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;

import java.util.List;

public interface VideoCategoryService {
    void addCategoriesToVideo(List<String> categories, String videoId, User user);
}
