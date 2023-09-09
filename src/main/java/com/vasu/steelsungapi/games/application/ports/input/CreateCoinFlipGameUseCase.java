package com.vasu.steelsungapi.games.application.ports.input;

import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;

public interface CreateCoinFlipGameUseCase {
    void createCoinFlipGame(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest,String username);
}
