package com.hiennhatt.vod.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "video_playlist")
public class VideoPlaylist {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 16)
    @NotNull
    @ColumnDefault("(uuid_to_bin(uuid()))")
    @Column(name = "uid", nullable = false, length = 16)
    private String uid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @NotNull
    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;

}