package com.codify.shardingimpl.repository.gym.repo;

import com.codify.shardingimpl.repository.gym.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepo extends JpaRepository<Gym, Long> {

}
