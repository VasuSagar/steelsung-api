package com.vasu.steelsungapi.player.domain.service;

import com.vasu.steelsungapi.player.application.ports.input.GetBalanceUseCase;
import com.vasu.steelsungapi.player.application.ports.input.SetBalanceUseCase;
import com.vasu.steelsungapi.player.domain.model.GetBalanceResponse;
import com.vasu.steelsungapi.player.domain.model.SetBalanceRequest;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.BalancePersistenceAdapter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import lombok.AllArgsConstructor;

import java.text.ParseException;


@AllArgsConstructor
public class BalanceService implements GetBalanceUseCase, SetBalanceUseCase {
    private final BalancePersistenceAdapter balancePersistenceAdapter;

    @Override
    public GetBalanceResponse getBalance() {
        return balancePersistenceAdapter.getBalance();
    }

    @Override
    public GetBalanceResponse setBalance(SetBalanceRequest setBalanceRequest, User user) throws ParseException {
        return balancePersistenceAdapter.setBalance(setBalanceRequest,user);
    }
}
