package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.History;
import com.hiennhatt.vod.repositories.projections.HistoryProjection;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class HistoryDTO {
    private final UUID uid;
    private final Instant createdOn;
    private final VideoOverviewDTO video;

    public HistoryDTO(History history) {
        this.uid = history.getUid();
        this.createdOn = history.getCreatedOn();
        this.video = new VideoOverviewDTO(history.getVideo());
    }

    public HistoryDTO(HistoryProjection history) {
        uid = history.getUid();
        createdOn = history.getCreatedOn();
        video = new VideoOverviewDTO(history.getVideo());
    }
}
