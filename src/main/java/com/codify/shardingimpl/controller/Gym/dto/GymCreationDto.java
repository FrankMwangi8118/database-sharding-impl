package com.codify.shardingimpl.controller.Gym.dto;

import com.codify.shardingimpl.repository.gym.model.Gym;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymCreationDto {
    private String name;
    private String location;



    public Gym toGym(Long id) {
        return Gym.builder()
                .id(id)
                .name(name)
                .location(location)
                .build();
    }
}
