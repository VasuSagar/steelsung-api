package com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.repository;

import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.entity.CoinFlipGameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinFlipGameStatusRepository extends JpaRepository<CoinFlipGameStatus,Long> {
    CoinFlipGameStatus findByCoinFlipGameDetailsId(Long id);
}
