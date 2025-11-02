package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.models.VideoCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Integer> {
    List<VideoCategory> findVideoCategoriesByVideoUid(UUID videoId);
    List<VideoCategory> findVideoCategoriesByVideo(Video video);
    List<VideoCategory> findVideoCategoriesByCategory(Category category, Pageable pageable);
}
