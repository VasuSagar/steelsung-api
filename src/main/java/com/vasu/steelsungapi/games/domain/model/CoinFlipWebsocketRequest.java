package com.vasu.steelsungapi.games.domain.model;

import com.vasu.steelsungapi.games.domain.util.CoinFlipGameRequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinFlipWebsocketRequest {
    CoinFlipGameRequestType coinFlipRequestType;
    CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest;
    CoinFlipMatchJoinWsRequest coinFlipMatchJoinWsRequest;
}
