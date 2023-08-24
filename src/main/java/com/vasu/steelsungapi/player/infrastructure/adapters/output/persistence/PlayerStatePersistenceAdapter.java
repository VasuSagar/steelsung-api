package com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.player.application.ports.output.PlayerStateOutputPort;
import com.vasu.steelsungapi.player.domain.model.GetPlayerStateResponse;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.entity.PlayerState;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository.PlayerStateRepository;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response.ResourceNotFoundException;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerStatePersistenceAdapter implements PlayerStateOutputPort {
    private final PlayerStateRepository playerStateRepository;
    private final RegisterPersistenceAdapter registerPersistenceAdapter;

    @Override
    public GetPlayerStateResponse getPlayerState() {
        User loggedInUser = registerPersistenceAdapter.getLoggedInUser();
        PlayerState playerState = playerStateRepository.findById(loggedInUser.getId()).orElseThrow(()->new ResourceNotFoundException("Player","id",loggedInUser.getId()));
        return GetPlayerStateResponse.builder().experience(playerState.getExperience()).level(playerState.getLevel()).totalBetAmount(playerState.getTotalBetAmount()).currentBalanceAmount(playerState.getBalanceAmount()).username(loggedInUser.getUsername()).build();
    }
}
