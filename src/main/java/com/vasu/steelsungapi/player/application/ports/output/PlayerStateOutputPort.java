package com.vasu.steelsungapi.player.application.ports.output;

import com.vasu.steelsungapi.player.domain.model.GetBalanceResponse;
import com.vasu.steelsungapi.player.domain.model.GetPlayerStateResponse;

public interface PlayerStateOutputPort {
    GetPlayerStateResponse getPlayerState();
}
