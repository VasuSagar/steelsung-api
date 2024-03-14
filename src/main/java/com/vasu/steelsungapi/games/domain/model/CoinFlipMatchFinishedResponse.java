package com.vasu.steelsungapi.games.domain.model;

import com.vasu.steelsungapi.games.domain.util.GameEventTypeResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinFlipMatchFinishedResponse {
    @Builder.Default
    private Integer eventType= GameEventTypeResponse.COIN_FLIP_GAME_OVER.ordinal();
    private String winnerUserName;
    private Long gameFinishedAt;
    private Long gameId;
    private Double winnerUpdatedBalance;
}
