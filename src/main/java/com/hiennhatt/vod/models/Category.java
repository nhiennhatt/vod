package com.hiennhatt.vod.models;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@Table(name = "categories")
@DynamicInsert
public class Category extends Persistence {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

}