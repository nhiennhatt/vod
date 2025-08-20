package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.IdentifiableVideoProjection;
import com.hiennhatt.vod.repositories.projections.VideoDetailProjection;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoByUid(UUID uid);

    @Query("SELECT v FROM Video v WHERE v.uid = :uid")
    VideoOverviewProjection getVideoOverview(UUID uid);

    @Query("SELECT v FROM Video v where v.uid = :uid")
    VideoDetailProjection getVideoDetail(UUID uid);

    IdentifiableVideoProjection findIdentifiableVideoProjectionByUid(UUID uid);

    @Modifying
    @Query("UPDATE Video v SET v.title = :title, v.description = :description, v.privacy = :privacy WHERE v.uid = :uid AND v.user.id = :userid")
    int updateVideo(@Param("uid") UUID uid, @Param("title") String title, @Param("description") String description, @Param("privacy") Video.Privacy privacy, @Param("userid") Integer userId);

    @Modifying
    @Query("DELETE FROM Video WHERE id = :id")
    int deleteVideoById(@Param("id") Integer id);

    Video getVideoByUid(UUID uid);
}
