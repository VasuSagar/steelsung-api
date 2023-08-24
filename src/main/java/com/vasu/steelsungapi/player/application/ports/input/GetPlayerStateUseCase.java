package com.vasu.steelsungapi.player.application.ports.input;

import com.vasu.steelsungapi.player.domain.model.GetPlayerStateResponse;

public interface GetPlayerStateUseCase {
    GetPlayerStateResponse getPlayerState();
}
