package com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository;

import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.entity.PlayerState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStateRepository extends JpaRepository<PlayerState,Long> {
}
