package com.hiennhatt.vod.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    public enum Status {
        ACTIVE, INACTIVE, DELETED
    }

    public enum Role {
        ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR
    }

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private boolean isVerified;

    @Column
    @CreationTimestamp
    private Instant createdOn;

    @Column
    @UpdateTimestamp
    private Instant updatedOn;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
