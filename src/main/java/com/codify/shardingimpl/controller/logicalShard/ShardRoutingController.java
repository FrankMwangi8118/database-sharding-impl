package com.codify.shardingimpl.controller.logicalShard;


import com.codify.shardingimpl.controller.logicalShard.dto.ShardRoutingDto;
import com.codify.shardingimpl.service.ShardRoutingImplService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/logical-shard")
public class ShardRoutingController {
    private final ShardRoutingImplService shardRoutingImplService;

    @GetMapping
    public List<ShardRoutingDto> get(@RequestParam(required = false) Integer id) {

        return shardRoutingImplService.fetch(id);
    }

    @GetMapping("/extract-logical-id")
    public Long extract(@RequestParam() Long id) {
        return shardRoutingImplService.extractLogicalId(id);

    }


}
