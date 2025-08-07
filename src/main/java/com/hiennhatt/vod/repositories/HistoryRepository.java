package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Integer> {
}
