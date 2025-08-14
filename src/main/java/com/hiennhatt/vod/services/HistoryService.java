package com.hiennhatt.vod.services;

import com.hiennhatt.vod.dtos.HistoryDTO;
import com.hiennhatt.vod.models.User;

import java.util.List;
import java.util.UUID;

public interface HistoryService {
    void saveHistory(String videoId, User user);
    List<HistoryDTO> getPersonalHistories(User user);
    void deleteHistoryRecord(UUID historyId, User user);
}
