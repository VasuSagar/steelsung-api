package com.vasu.steelsungapi.games.application.ports.output;

import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchJoinWsRequest;

public interface CoinFlipGamePublisher {
    void createCoinFlipGame(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest,String username);

    void joinCoinFlipGame(CoinFlipMatchJoinWsRequest coinFlipMatchJoinWsRequest, String username);
}
