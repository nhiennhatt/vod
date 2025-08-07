package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.History;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.HistoryRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new IllegalArgumentException("Video not found with ID: " + videoId);
        }
        
        History history = new History();
        history.setUid(UUID.randomUUID());
        history.setUser(user);
        history.setVideo(video);
        
        historyRepository.save(history);
    }
}