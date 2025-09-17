package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Subscribe;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<SubscribeProjection> findSubscribeProjectionsBySourceUser(User sourceUser);

    List<SubscribeProjection> findSubscribeProjectionsByDestUser(User targetUser);

    long countSubscribesByDestUser(User targetUser);

    boolean existsBySourceUserAndDestUser(User sourceUser, User destUser);

    Subscribe findSubscribeBySourceUserAndDestUser(User sourceUser, User destUser);
}
