package com.vasu.steelsungapi.games.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinFlipMatchJoinWsRequest {
    private Long matchId;
}
