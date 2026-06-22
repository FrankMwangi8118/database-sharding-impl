package com.codify.shardingimpl.controller.Gym.dto;

import com.codify.shardingimpl.repository.gym.model.Gym;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymDto {
    private Long id;
    private String name;
    private String location;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    public Gym toGym() {
        return Gym.builder()
                .id(id)
                .name(name)
                .location(location)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
