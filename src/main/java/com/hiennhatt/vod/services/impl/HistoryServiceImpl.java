package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.HistoryDTO;
import com.hiennhatt.vod.models.History;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.HistoryRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        if (user == null) {
            return; // User not provided, don't save history
        }

        Video video = videoRepository.findVideoByUid(UUID.fromString(videoId));
        if (video == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }

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
}