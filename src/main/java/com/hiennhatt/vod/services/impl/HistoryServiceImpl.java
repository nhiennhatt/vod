package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.HistoryDTO;
import com.hiennhatt.vod.models.History;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.HistoryRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.HistoryService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    @Transactional
    public void saveHistory(String videoId, User user) {
        Video video = videoRepository.findVideoByUid(UUID.fromString(videoId));
        if (video == null)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        History history = new History();
        history.setUid(UUID.randomUUID());
        history.setUser(user);
        history.setVideo(video);

        historyRepository.save(history);
    }

    @Override
    @Transactional
    public List<HistoryDTO> getPersonalHistories(User user) {
        return historyRepository.getHistoryByUser(user).stream().map(HistoryDTO::new).toList();
    }

    @Override
    @Transactional
    public void deleteHistoryRecord(UUID historyId, User user) {
        History history = historyRepository.findHistoryByUid(historyId);
        if (history == null)
            throw new HTTPResponseStatusException("History not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        if (!history.getUser().getId().equals(user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);
        historyRepository.delete(history);
    }
}