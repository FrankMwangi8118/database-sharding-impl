package com.codify.shardingimpl.repository.gym.model;

import com.codify.shardingimpl.controller.Gym.dto.GymDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.lang.annotation.Annotation;
import java.time.OffsetDateTime;

@Entity
@Table(name = "gym")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gym  implements Persistable<Long> {
    @Id
    @Column(name = "id")
    private Long id;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public GymDto toGymDto() {
        return GymDto.builder()
                .id(id)
                .name(name)
                .location(location)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @PostPersist
    @PostLoad
    public void markNotNew() {
        isNew = false;
    }
}
