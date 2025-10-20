package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.dtos.LikeDTO;
import com.hiennhatt.vod.models.Like;
import com.hiennhatt.vod.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    long countLikesByVideo_Uid(UUID videoUid);
    boolean existsLikeByVideoUidAndUser(UUID videoUid, User user);
    Like findLikeByVideoUidAndUser(UUID videoUid, User user);
    @Query("SELECT new com.hiennhatt.vod.dtos.LikeDTO(l) FROM Like l WHERE l.user = :user and l.video.status <> com.hiennhatt.vod.models.Video.Status.INACTIVE and l.video.privacy <> com.hiennhatt.vod.models.Video.Privacy.PRIVATE")
    List<LikeDTO> findLikesOfUser(User user);
}
