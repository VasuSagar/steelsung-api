package com.vasu.steelsungapi.games.application.ports.input;

import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchJoinWsRequest;

public interface CreateCoinFlipGameUseCase {
    void createCoinFlipGame(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest,String username);

    void joinCoinFlipGame(CoinFlipMatchJoinWsRequest coinFlipMatchJoinWsRequest, String username);
}
