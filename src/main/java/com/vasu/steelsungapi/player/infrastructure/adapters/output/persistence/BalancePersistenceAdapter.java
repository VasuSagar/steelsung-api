package com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.player.application.ports.output.BalanceOutputPort;

public class BalancePersistenceAdapter implements BalanceOutputPort {

    @Override
    public Double getBalance() {
        return Double.valueOf(1000.5);
    }
}
