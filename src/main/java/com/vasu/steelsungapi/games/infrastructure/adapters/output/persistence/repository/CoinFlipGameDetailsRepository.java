package com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.repository;

import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.entity.CoinFlipGameDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinFlipGameDetailsRepository extends JpaRepository<CoinFlipGameDetails,Long> {
}
