package com.vasu.steelsungapi.games.domain.model;

import com.vasu.steelsungapi.games.domain.util.GameEventTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinFlipMatchResponse {
    @Builder.Default
    private Integer eventType= GameEventTypeResponse.COIN_FLIP_GAME_CREATED.ordinal();
    private Long gameCreaterId;
    private String gameCreaterUsername;
    private Long gameCreatedAt;
    private Long gameId;
    private Double matchBetAmount;
    private Integer creatorCoinSide;
}
