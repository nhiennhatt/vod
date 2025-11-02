package com.hiennhatt.vod.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "videos")
@DynamicInsert
public class Video {
    public enum Status {
        PROCESSING, FAILED, ACTIVE, INACTIVE, VIOLATED
    }

    public enum Privacy {
        PUBLIC, PRIVATE, LIMITED
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ColumnDefault("(uuid_to_bin(uuid()))")
    @Column(name = "uid", nullable = false, length = 16)
    private UUID uid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 50)
    @NotNull
    @Column(name = "thumbnail", nullable = false, length = 50)
    private String thumbnail;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 50)
    @NotNull
    @Column(name = "file_name", nullable = false, length = 50)
    private String fileName;

    @ColumnDefault("'ACTIVE'")
    @Lob
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Lob
    @Column(name = "privacy", nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @Column(name = "created_on")
    @CreationTimestamp
    private Instant createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Instant updatedOn;

    @ManyToMany
    @JoinTable(name = "video_categories", joinColumns = {@JoinColumn(name = "video_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories = new ArrayList<>();
}