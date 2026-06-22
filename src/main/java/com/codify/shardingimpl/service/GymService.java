package com.codify.shardingimpl.service;

import com.codify.shardingimpl.config.DataSourceContextHolder;
import com.codify.shardingimpl.config.ShardRoutingConfigLoader;
import com.codify.shardingimpl.controller.Gym.dto.GymCreationDto;
import com.codify.shardingimpl.controller.Gym.dto.GymDto;
import com.codify.shardingimpl.repository.gym.repo.GymRepo;
import com.codify.shardingimpl.util.DataSourceContextSetter;
import com.codify.shardingimpl.util.SnowFlakeIdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Service
@AllArgsConstructor
public class GymService {
    private final GymRepo gymRepo;
    private final DataSourceContextSetter dataSourceContextSetter;
    private final SnowFlakeIdGenerator snowFlakeIdGenerator;
    private final ShardRoutingConfigLoader shardRoutingConfigLoader;

    public GymDto createGym(GymCreationDto gymDto) throws SQLException {
        var id = snowFlakeIdGenerator.generateId();
        var toSave = gymDto.toGym(id);


        log.info("Creating Gym {}", toSave.getName());
        try {
            dataSourceContextSetter.setDataSource(getDatasource(id));
            var response = gymRepo.save(toSave);
            return response.toGymDto();
        } finally {
            DataSourceContextHolder.clear();
        }
    }


    private String getDatasource(Long id) {
        return shardRoutingConfigLoader.getDataSourceNameById(id);
    }
}
