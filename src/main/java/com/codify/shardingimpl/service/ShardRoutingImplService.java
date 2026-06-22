package com.codify.shardingimpl.service;

import com.codify.shardingimpl.controller.logicalShard.dto.ShardRoutingDto;
import com.codify.shardingimpl.repository.ShardRoutingRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class ShardRoutingImplService {

    private final ShardRoutingRepo shardRoutingRepo;

    public ShardRoutingDto create(ShardRoutingDto shardRoutingDto) {
        var saved = shardRoutingRepo.save(shardRoutingDto.toShardRouting(shardRoutingDto));
        return saved.toShardRoutingDto(saved);

    }

    public ShardRoutingDto findById(Integer id) {
        var model = shardRoutingRepo.findById(id).get();
        return model.toShardRoutingDto(model);

    }

    public List<ShardRoutingDto> fetchAll() {
        return shardRoutingRepo.findAll()
                .stream()
                .map(model -> model.toShardRoutingDto(model))
                .toList();
    }
    public List<ShardRoutingDto> fetch(Integer id) {
        if (Objects.isNull(id)) {
            return fetchAll();
        }
        var res = findById(id);
        return List.of(res);
    }


}
