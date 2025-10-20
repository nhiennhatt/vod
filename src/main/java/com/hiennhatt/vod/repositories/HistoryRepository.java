package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.History;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.HistoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<HistoryProjection> getHistoryByUser(User user);

    History findHistoryByUid(UUID uid);
}
