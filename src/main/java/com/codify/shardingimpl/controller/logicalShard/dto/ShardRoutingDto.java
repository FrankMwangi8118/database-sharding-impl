package com.codify.shardingimpl.controller.logicalShard.dto;

import com.codify.shardingimpl.repository.Model.ShardRouting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShardRoutingDto {

    private Long id;
    private String physicalNodeName;
    private String physicalNodeHost;
    private Integer physicalNodePort;
    private String status;


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
