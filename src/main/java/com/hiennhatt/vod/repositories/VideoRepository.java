package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoById(Integer id);

    Video findVideoByUid(UUID uid);

    @Query("SELECT new com.hiennhatt.vod.repositories.projections.VideoOverview(v.uid, v.title, v.description, v.privacy, v.status, v.thumbnail, new com.hiennhatt.vod.repositories.projections.UserOverview(v.user.username, v.user.userInform.firstName, v.user.userInform.lastName, v.user.userInform.avatar)) FROM Video v WHERE v.uid = :uid")
    VideoOverview getVideoOverview(UUID uid);
}
