package com.vasu.steelsungapi.games.domain.model;

import com.vasu.steelsungapi.games.domain.util.GameEventTypeResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinFlipMatchJoinedResponse {
    @Builder.Default
    private Integer eventType= GameEventTypeResponse.COIN_FLIP_GAME_PARTICIPANT_JOINED.ordinal();
    private String participantJoinedUsername;
    private Long participantJoinedAt;
    private Long gameId;
    private Integer participantCoinSide;
    private Long participantJoinedId;
}
