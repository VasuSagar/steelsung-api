package com.vasu.steelsungapi.games.application.ports.output;

import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;

public interface CoinFlipGamePublisher {
    void createCoinFlipGame(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest,String username);
}
