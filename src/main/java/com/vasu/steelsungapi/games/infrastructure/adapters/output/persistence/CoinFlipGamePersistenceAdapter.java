package com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.games.application.ports.output.CoinFlipGamePublisher;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchErrorResponse;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchResponse;
import com.vasu.steelsungapi.games.domain.util.CoinFlipGameErrorEventResponse;
import com.vasu.steelsungapi.games.domain.util.GameEventTypeResponse;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.entity.CoinFlipGameDetails;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.entity.CoinFlipGameStatus;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.repository.CoinFlipGameDetailsRepository;
import com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.repository.CoinFlipGameStatusRepository;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.entity.PlayerState;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository.PlayerStateRepository;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response.ResourceNotFoundException;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;

@RequiredArgsConstructor
public class CoinFlipGamePersistenceAdapter implements CoinFlipGamePublisher {
    private final RegisterPersistenceAdapter registerPersistenceAdapter;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final CoinFlipGameDetailsRepository coinFlipGameDetailsRepository;
    private final CoinFlipGameStatusRepository coinFlipGameStatusRepository;
    private final PlayerStateRepository playerStateRepository;

    @Override
    @Transactional
    public void createCoinFlipGame(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest, String username) {
        User loggedInUser = registerPersistenceAdapter.getLoggedInUserByUsername(username);
        PlayerState playerStateToUpdate = playerStateRepository.findById(loggedInUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Player", "id", loggedInUser.getId()));
        double roundedOffBalanceValue = convertBalanceAmount(coinFlipMatchCreateWsRequest.getMatchBetAmount());

        boolean isRequestInvalid = validateCoinFlipMatchCreateWsRequest(coinFlipMatchCreateWsRequest, playerStateToUpdate.getBalanceAmount());
        if (isRequestInvalid) {
            return;
        }

        CoinFlipGameDetails coinFlipGameDetails = CoinFlipGameDetails.builder().user(loggedInUser).betAmount(coinFlipMatchCreateWsRequest.getMatchBetAmount()).creatorCoinSide(coinFlipMatchCreateWsRequest.getCreatorCoinSide()).build();
        CoinFlipGameDetails savedCoinFlipGameDetails = coinFlipGameDetailsRepository.save(coinFlipGameDetails);

        CoinFlipGameStatus coinFlipGameStatus = CoinFlipGameStatus.builder().coinFlipGameDetails(savedCoinFlipGameDetails).gameStatus(GameEventTypeResponse.COIN_FLIP_GAME_CREATED.ordinal()).gameCreatedAt(Instant.now().toEpochMilli()).build();
        CoinFlipGameStatus savedCoinFlipGameStatus = coinFlipGameStatusRepository.save(coinFlipGameStatus);

        Double updatedBalanceAmount = playerStateToUpdate.getBalanceAmount() - roundedOffBalanceValue;
        //TODO: Does this require conversion to 2 floating point?
        playerStateToUpdate.setBalanceAmount(updatedBalanceAmount);
        playerStateRepository.save(playerStateToUpdate);

        CoinFlipMatchResponse response = CoinFlipMatchResponse.builder().gameCreatedAt(savedCoinFlipGameStatus.getGameCreatedAt()).gameCreaterId(savedCoinFlipGameStatus.getCoinFlipGameDetails().getUser().getId()).matchBetAmount(savedCoinFlipGameStatus.getCoinFlipGameDetails().getBetAmount()).gameId(savedCoinFlipGameStatus.getId()).gameCreaterUsername(savedCoinFlipGameStatus.getCoinFlipGameDetails().getUser().getUsername()).creatorCoinSide(coinFlipMatchCreateWsRequest.getCreatorCoinSide()).build();
        this.simpMessagingTemplate.convertAndSend("/topic/coinflip", response);
    }

    //TODO:Use Jakarta validation or alternative instead of user defined functions
    private boolean validateCoinFlipMatchCreateWsRequest(CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest, Double currentBalanceAmount) {
        return validateCoinSide(coinFlipMatchCreateWsRequest.getCreatorCoinSide()) || validateMatchBetAmount(coinFlipMatchCreateWsRequest.getMatchBetAmount(), currentBalanceAmount);
    }

    private boolean validateMatchBetAmount(Double matchBetAmount, Double playerCurrentBalanceAmount) {
        if (playerCurrentBalanceAmount < matchBetAmount) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_LOW_BALANCE.ordinal()).errorMessage("User's current balance is low than the bet amount").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }
        if (matchBetAmount <= 0.1 || matchBetAmount >= 1000) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_INVALID_BET_AMOUNT.ordinal()).errorMessage("Bet amount should be between 0.1-1000").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }
        return false;
    }

    private boolean validateCoinSide(Integer creatorCoinSide) {
        if (creatorCoinSide >= 2 || creatorCoinSide < 0) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_INVALID_COIN_SIDE.ordinal()).errorMessage("Coin side should be 0 or 1").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }
        return false;
    }

    private double convertBalanceAmount(Double amount) {
        double val = amount * 100;
        val = (double) ((int) val);
        return val / 100;
    }
}
