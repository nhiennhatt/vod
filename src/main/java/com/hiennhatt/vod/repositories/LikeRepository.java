package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Like;
import com.hiennhatt.vod.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    long countLikesByVideo_Uid(UUID videoUid);
    boolean existsLikeByVideoUidAndUser(UUID videoUid, User user);
    Like findLikeByVideoUidAndUser(UUID videoUid, User user);
}
