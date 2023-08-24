package com.vasu.steelsungapi.player.domain.service;

import com.vasu.steelsungapi.player.application.ports.input.GetPlayerStateUseCase;
import com.vasu.steelsungapi.player.domain.model.GetPlayerStateResponse;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.PlayerStatePersistenceAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerStateService implements GetPlayerStateUseCase {
    private final PlayerStatePersistenceAdapter playerStatePersistenceAdapter;

    @Override
    public GetPlayerStateResponse getPlayerState() {
        return playerStatePersistenceAdapter.getPlayerState();
    }
}
