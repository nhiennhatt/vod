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
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "notifications")
@DynamicInsert
public class Notification {
    public enum Status {
        SEND, SEEN, INACTIVE
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ColumnDefault("(uuid_to_bin(uuid()))")
    @Column(name = "uid", nullable = false, length = 16)
    private UUID uid;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Size(max = 255)
    @Column(name = "next")
    private String next;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_user", nullable = false)
    private User toUser;

    @Column(name = "created_on", nullable = false)
    @CreationTimestamp
    private Instant createdOn;

    @Column(name = "updated_on", nullable = false)
    @UpdateTimestamp
    private Instant updatedOn;

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

}