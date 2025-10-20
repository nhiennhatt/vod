package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.IdentifiableVideoProjection;
import com.hiennhatt.vod.repositories.projections.VideoDetailProjection;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoByUid(UUID uid);

    @Query("SELECT v FROM Video v WHERE v.uid = :uid")
    VideoOverviewProjection getVideoOverview(UUID uid);

    @Query("SELECT v FROM Video v where v.uid = :uid")
    VideoDetailProjection getVideoDetail(UUID uid);

    @Query("SELECT v FROM Subscribe s LEFT JOIN Video v ON v.user = s.destUser WHERE s.sourceUser = :sourceUser and v.status = com.hiennhatt.vod.models.Video.Status.ACTIVE ORDER BY v.createdOn desc")
    List<VideoOverviewProjection> findVideosSubscribedByUser(@Param("sourceUser") User sourceUser, Pageable pageable);

    IdentifiableVideoProjection findIdentifiableVideoProjectionByUid(UUID uid);

    @Query("SELECT v FROM Video v WHERE v.status = com.hiennhatt.vod.models.Video.Status.ACTIVE and v.privacy = com.hiennhatt.vod.models.Video.Privacy.PUBLIC")
    List<VideoOverviewProjection> getNewestVideos(Pageable pageable);

    @Modifying
    @Query("UPDATE Video v SET v.title = :title, v.description = :description, v.privacy = :privacy WHERE v.uid = :uid AND v.user.id = :userid")
    int updateVideo(@Param("uid") UUID uid, @Param("title") String title, @Param("description") String description, @Param("privacy") Video.Privacy privacy, @Param("userid") Integer userId);

    @Modifying
    @Query("DELETE FROM Video WHERE id = :id")
    int deleteVideoById(@Param("id") Integer id);

    Video getVideoByUid(UUID uid);

    List<VideoOverviewProjection> findVideoOverviewProjectionsByTitleLikeOrDescriptionLikeAndStatusAndPrivacy(@Size(max = 255) String title, String description, Video.Status status, Video.Privacy privacy, Pageable pageable);

    @Query("SELECT v from Video v WHERE v.user.username = :username and v.status = com.hiennhatt.vod.models.Video.Status.ACTIVE and v.privacy = com.hiennhatt.vod.models.Video.Privacy.PUBLIC")
    List<VideoOverviewProjection> findVideosOwnByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT v from Video v WHERE v.user = :user and v.status <> com.hiennhatt.vod.models.Video.Status.INACTIVE")
    List<VideoOverviewProjection> findMyVideos(User user, Pageable pageable);

    @Modifying
    @Query("UPDATE Video v SET v.status = com.hiennhatt.vod.models.Video.Status.FAILED WHERE v.uid = :uid")
    int updateVideoStatusToFailed(@Param("uid") UUID uid);

    long countVideosByUserAndStatusAndPrivacy(User user, Video.Status status, Video.Privacy privacy);
}
