package com.vasu.steelsungapi.player.infrastructure.adapters.config;

import com.vasu.steelsungapi.player.domain.service.BalanceService;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.BalancePersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public BalancePersistenceAdapter balancePersistenceAdapter() {
        return new BalancePersistenceAdapter();
    }

    @Bean
    public BalanceService balanceService(BalancePersistenceAdapter balancePersistenceAdapter) {
        return new BalanceService(balancePersistenceAdapter);
    }
}
