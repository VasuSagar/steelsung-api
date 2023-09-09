package com.vasu.steelsungapi.games.infrastructure.adapters.config;

import com.vasu.steelsungapi.games.domain.service.CoinFlipGameService;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.CoinFlipGamePersistenceAdapter;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.repository.CoinFlipGameDetailsRepository;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.repository.CoinFlipGameStatusRepository;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository.PlayerStateRepository;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class CoinFlipGameBeanConfiguration {
    @Bean
    public CoinFlipGamePersistenceAdapter coinFlipGamePersistenceAdapter(RegisterPersistenceAdapter registerPersistenceAdapter, SimpMessagingTemplate simpMessagingTemplate, CoinFlipGameDetailsRepository coinFlipGameDetailsRepository, CoinFlipGameStatusRepository coinFlipGameStatusRepository, PlayerStateRepository playerStateRepository){
        return new CoinFlipGamePersistenceAdapter(registerPersistenceAdapter,simpMessagingTemplate,coinFlipGameDetailsRepository,coinFlipGameStatusRepository,playerStateRepository);
    }

    @Bean
    public CoinFlipGameService coinFlipGameService(CoinFlipGamePersistenceAdapter coinFlipGamePersistenceAdapter){
        return new CoinFlipGameService(coinFlipGamePersistenceAdapter);
    }
}
