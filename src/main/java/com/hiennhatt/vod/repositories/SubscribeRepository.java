package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Subscribe;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import com.hiennhatt.vod.repositories.projections.SubscriptionProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<SubscribeProjection> findSubscribeProjectionsBySourceUser(User sourceUser);

    List<SubscribeProjection> findSubscribeProjectionsByDestUser(User targetUser);

    long countSubscribesByDestUser(User targetUser);

    boolean existsBySourceUserAndDestUser(User sourceUser, User destUser);

    Subscribe findSubscribeBySourceUserAndDestUser(User sourceUser, User destUser);

    @Query("SELECT s FROM Subscribe s where s.sourceUser = ?1 and s.destUser.status = com.hiennhatt.vod.models.User.Status.ACTIVE")
    List<SubscriptionProjection> findSubscription(User sourceUser, Pageable pageable);
}
