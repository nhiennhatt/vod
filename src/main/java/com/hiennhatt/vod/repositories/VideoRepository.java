package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.VideoDetail;
import com.hiennhatt.vod.repositories.projections.VideoOverview;
import com.hiennhatt.vod.validations.UpdateVideoValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoByUid(UUID uid);

    @Query("SELECT new com.hiennhatt.vod.repositories.projections.VideoOverview(v.uid, v.title, v.privacy, v.status, v.thumbnail, new com.hiennhatt.vod.repositories.projections.UserOverviewDTO(v.user.username, v.user.userInform.firstName, v.user.userInform.lastName, v.user.userInform.avatar)) FROM Video v WHERE v.uid = :uid")
    VideoOverview getVideoOverview(UUID uid);

    @Query("SELECT new com.hiennhatt.vod.repositories.projections.VideoDetail(v.uid, v.title, v.description, v.status, v.privacy, v.fileName, v.thumbnail, new com.hiennhatt.vod.repositories.projections.UserOverviewDTO(v.user.username, v.user.userInform.firstName, v.user.userInform.lastName, v.user.userInform.avatar)) FROM Video v where v.uid = :uid")
    VideoDetail getVideoDetail(UUID uid);

    @Modifying
    @Query("UPDATE Video v SET v.title = :title, v.description = :description, v.privacy = :privacy WHERE v.uid = :uid AND v.user.id = :userid")
    int updateVideo(@Param("uid") UUID uid, @Param("title") String title, @Param("description") String description, @Param("privacy") Video.Privacy privacy, @Param("userid") Integer userId);

    @Modifying
    @Query("DELETE FROM Video WHERE id = :id")
    int deleteVideoById(@Param("id") Integer id);

    Video getVideoByUid(UUID uid);
}
