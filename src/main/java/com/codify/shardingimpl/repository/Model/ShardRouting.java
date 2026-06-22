package com.codify.shardingimpl.repository.Model;

import com.codify.shardingimpl.controller.logicalShard.dto.ShardRoutingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShardRouting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "physical_node_name")
    private String physicalNodeName;

    @Column(name = "physical_node_host")
    private String physicalNodeHost;

    @Column(name = "physical_node_port")
    private Integer physicalNodePort;

    @Column(name = "status")
    private String status;

    @Column(name = "createdAt")
    private OffsetDateTime createdAt;

    @Column(name = "updatedAt")
    private OffsetDateTime updatedAt;


    public ShardRoutingDto toShardRoutingDto(ShardRouting shardRoutingDto) {
        return ShardRoutingDto.builder()
                .id(id)
                .physicalNodeName(physicalNodeName)
                .physicalNodeHost(physicalNodeHost)
                .physicalNodePort(physicalNodePort)
                .status(status)
                .build();
    }

}
