package com.codify.shardingimpl.repository;

import com.codify.shardingimpl.repository.Model.ShardRouting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShardRoutingRepo extends JpaRepository<ShardRouting, Integer> {
}
