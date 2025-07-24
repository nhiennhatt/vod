package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.models.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Integer> {
    VideoCategory findVideoCategoryById(Integer id);
    List<VideoCategory> findVideoCategoriesByVideoId(Integer videoId);
    List<VideoCategory> findVideoCategoriesByVideo(Video video);
}
