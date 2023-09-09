package com.vasu.steelsungapi.games.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinFlipMatchCreateWsRequest {
    private Double matchBetAmount;
    private Integer creatorCoinSide;
}
