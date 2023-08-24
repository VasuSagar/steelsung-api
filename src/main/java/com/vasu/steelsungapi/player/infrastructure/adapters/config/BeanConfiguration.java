package com.vasu.steelsungapi.player.infrastructure.adapters.config;

import com.vasu.steelsungapi.player.domain.service.BalanceService;
import com.vasu.steelsungapi.player.domain.service.PlayerStateService;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.BalancePersistenceAdapter;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.PlayerStatePersistenceAdapter;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository.PlayerStateRepository;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public BalancePersistenceAdapter balancePersistenceAdapter(PlayerStateRepository playerStateRepository, RegisterPersistenceAdapter registerPersistenceAdapter) {
        return new BalancePersistenceAdapter(playerStateRepository,registerPersistenceAdapter);
    }

    @Bean
    public BalanceService balanceService(BalancePersistenceAdapter balancePersistenceAdapter) {
        return new BalanceService(balancePersistenceAdapter);
    }

    @Bean
    public PlayerStatePersistenceAdapter playerStatePersistenceAdapter(PlayerStateRepository playerStateRepository, RegisterPersistenceAdapter registerPersistenceAdapter) {
        return new PlayerStatePersistenceAdapter(playerStateRepository,registerPersistenceAdapter);
    }

    @Bean
    public PlayerStateService playerStateService(PlayerStatePersistenceAdapter playerStatePersistenceAdapter) {
        return new PlayerStateService(playerStatePersistenceAdapter);
    }
}
