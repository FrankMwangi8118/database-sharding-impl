package com.codify.shardingimpl.controller.Gym;

import com.codify.shardingimpl.controller.Gym.dto.GymCreationDto;
import com.codify.shardingimpl.controller.Gym.dto.GymDto;
import com.codify.shardingimpl.service.GymService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/gym")
public class GymController {
    private final GymService gymService;

    @PostMapping
    public GymDto createGym(@RequestBody GymCreationDto gymDto) throws SQLException {
        return gymService.createGym(gymDto);
    }


}
