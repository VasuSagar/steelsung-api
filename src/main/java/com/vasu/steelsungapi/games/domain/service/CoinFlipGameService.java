package com.vasu.steelsungapi.games.domain.service;

import com.vasu.steelsungapi.games.application.ports.input.CreateCoinFlipGameUseCase;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.CoinFlipGamePersistenceAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoinFlipGameService implements CreateCoinFlipGameUseCase {
    private final CoinFlipGamePersistenceAdapter coinFlipGamePersistenceAdapter;
    @Override
    public void createCoinFlipGame(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest,String username) {
        coinFlipGamePersistenceAdapter.createCoinFlipGame(coinFlipMatchCreateWsRequest,username);
    }
}
