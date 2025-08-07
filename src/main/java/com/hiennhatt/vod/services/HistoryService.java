package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;

public interface HistoryService {
    void saveHistory(String videoId, User user);
}
