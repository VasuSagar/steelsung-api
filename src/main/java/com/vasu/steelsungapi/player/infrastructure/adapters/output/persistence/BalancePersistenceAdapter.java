package com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.player.application.ports.output.BalanceOutputPort;
import com.vasu.steelsungapi.player.domain.model.GetBalanceResponse;
import com.vasu.steelsungapi.player.domain.model.SetBalanceRequest;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.entity.PlayerState;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository.PlayerStateRepository;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response.ResourceNotFoundException;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.text.ParseException;

@RequiredArgsConstructor
public class BalancePersistenceAdapter implements BalanceOutputPort {
    private final PlayerStateRepository playerStateRepository;
    private final RegisterPersistenceAdapter registerPersistenceAdapter;

    @Override
    public GetBalanceResponse getBalance() {
        User loggedInUser = registerPersistenceAdapter.getLoggedInUser();
        PlayerState playerState = playerStateRepository.findById(loggedInUser.getId()).orElseThrow(()->new ResourceNotFoundException("Player","id",loggedInUser.getId()));
        return GetBalanceResponse.builder().balanceAmount(playerState.getBalanceAmount()).build();
    }

    @Override
    public GetBalanceResponse setBalance(SetBalanceRequest setBalanceRequest, User user) throws ParseException {
        DecimalFormat df=new DecimalFormat("0.00");
        String format = df.format(setBalanceRequest.getBalanceAmount());
        Double roundedOffBalanceValue = (Double)df.parse(format) ;
        User loggedInUser = registerPersistenceAdapter.getLoggedInUser();

        PlayerState playerStateToUpdate = playerStateRepository.findById(loggedInUser.getId()).orElseThrow(()->new ResourceNotFoundException("Player","id",loggedInUser.getId()));
        Double updatedBalanceAmount = playerStateToUpdate.getBalanceAmount() + roundedOffBalanceValue;
        playerStateToUpdate.setBalanceAmount((Double)df.parse(df.format(updatedBalanceAmount)));
        PlayerState savedPlayerState = playerStateRepository.save(playerStateToUpdate);
        return GetBalanceResponse.builder().balanceAmount(savedPlayerState.getBalanceAmount()).build();
    }
}
