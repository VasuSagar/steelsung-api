package com.vasu.steelsungapi.player.domain.service;

import com.vasu.steelsungapi.player.application.ports.input.GetBalanceUseCase;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.BalancePersistenceAdapter;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class BalanceService implements GetBalanceUseCase {
    private final BalancePersistenceAdapter balancePersistenceAdapter;

    @Override
    public Double getBalance() {
        return balancePersistenceAdapter.getBalance();
    }
}
