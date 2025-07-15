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
@Table(name = "videos")
public class Video {
    public enum Status {
        PROCESSING, ACTIVE, INACTIVE, VIOLATE
    }

    public enum Privacy {
        PUBLIC, PRIVATE, LIMITED
    }

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 255)
    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Video.Status status;

    @NotNull
    @Column(name = "privacy", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Privacy privacy;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @NotNull
    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;

}