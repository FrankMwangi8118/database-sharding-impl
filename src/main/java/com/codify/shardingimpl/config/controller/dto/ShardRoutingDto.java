package com.codify.shardingimpl.config.controller.dto;

import com.codify.shardingimpl.repository.Model.ShardRouting;
import lombok.Builder;

@Builder
public class ShardRoutingDto {

    private Long id;
    private String physicalNodeName;
    private String physicalNodeHost;
    private Integer physicalNodePort;
    private Boolean status;


    public ShardRouting toShardRouting(ShardRoutingDto shardRoutingDto) {
        return ShardRouting.builder()
                .id(id)
                .physicalNodeName(physicalNodeName)
                .physicalNodeHost(physicalNodeHost)
                .physicalNodePort(physicalNodePort)
                .status(status)
                .build();
    }

}
