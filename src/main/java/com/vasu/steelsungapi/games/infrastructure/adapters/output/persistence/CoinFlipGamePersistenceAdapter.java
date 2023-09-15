package com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.games.application.ports.output.CoinFlipGamePublisher;
import com.vasu.steelsungapi.games.domain.model.*;
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

    @Override
    @Transactional
    public void joinCoinFlipGame(CoinFlipMatchJoinWsRequest coinFlipMatchJoinWsRequest, String username) {
        User loggedInUser = registerPersistenceAdapter.getLoggedInUserByUsername(username);
        CoinFlipGameDetails coinFlipGameDetails = coinFlipGameDetailsRepository.findById(coinFlipMatchJoinWsRequest.getMatchId()).orElseThrow(() -> new ResourceNotFoundException("Match id", "id", coinFlipMatchJoinWsRequest.getMatchId()));
        boolean isRequestInvalid=validateCoinFlipMatchJoinWsRequest(coinFlipGameDetails,loggedInUser);
        if (isRequestInvalid) {
            return;
        }

        PlayerState playerStateToUpdate = playerStateRepository.findById(loggedInUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Player", "id", loggedInUser.getId()));
        double roundedOffBalanceValue = convertBalanceAmount(coinFlipGameDetails.getBetAmount());

        //subtract bet amount from joined player's balance
        Double updatedBalanceAmount = playerStateToUpdate.getBalanceAmount() - roundedOffBalanceValue;
        playerStateToUpdate.setBalanceAmount(updatedBalanceAmount);
        playerStateRepository.save(playerStateToUpdate);

        //update game status
        CoinFlipGameStatus coinFlipGameStatus = coinFlipGameStatusRepository.findByCoinFlipGameDetailsId(coinFlipGameDetails.getId());
        coinFlipGameStatus.setGameStatus(GameEventTypeResponse.COIN_FLIP_GAME_PARTICIPANT_JOINED.ordinal());
        coinFlipGameStatus.setGameParticipantJoinedAt(Instant.now().toEpochMilli());
        CoinFlipGameStatus savedCoinFlipGameStatus = coinFlipGameStatusRepository.save(coinFlipGameStatus);

        //update game details
        coinFlipGameDetails.setParticipantUser(loggedInUser);
        coinFlipGameDetails.setParticipantCoinSide(1-coinFlipGameDetails.getCreatorCoinSide());
        coinFlipGameDetailsRepository.save(coinFlipGameDetails);

        CoinFlipMatchJoinedResponse response = CoinFlipMatchJoinedResponse.builder().participantJoinedAt(savedCoinFlipGameStatus.getGameParticipantJoinedAt()).participantJoinedId(savedCoinFlipGameStatus.getCoinFlipGameDetails().getParticipantUser().getId()).gameId(savedCoinFlipGameStatus.getId()).participantJoinedUsername(savedCoinFlipGameStatus.getCoinFlipGameDetails().getParticipantUser().getUsername()).participantCoinSide(savedCoinFlipGameStatus.getCoinFlipGameDetails().getParticipantCoinSide()).build();
        this.simpMessagingTemplate.convertAndSend("/topic/coinflip", response);
    }

    //TODO:Use Jakarta validation or alternative instead of user defined functions
    private boolean validateCoinFlipMatchJoinWsRequest(CoinFlipGameDetails coinFlipGameDetails, User joinedPlayer) {
        //check if joinedPlayer and matchCreator are not same
        if (coinFlipGameDetails.getUser().getId().equals(joinedPlayer.getId())) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_SAME_PLAYER.ordinal()).errorMessage("Match creator and joined player are same").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }

        //check if match is already joined
        CoinFlipGameStatus coinFlipGameStatus = coinFlipGameStatusRepository.findByCoinFlipGameDetailsId(coinFlipGameDetails.getId());
        if (coinFlipGameStatus.getGameStatus() == GameEventTypeResponse.COIN_FLIP_GAME_PARTICIPANT_JOINED.ordinal()) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_ALREADY_JOINED.ordinal()).errorMessage("Match is already joined").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }

        //check if match is already finished
        if (coinFlipGameStatus.getGameStatus() == GameEventTypeResponse.COIN_FLIP_GAME_OVER.ordinal()) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_ALREADY_FINISHED.ordinal()).errorMessage("Match is already finished").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }

        //check if user balance is low
        PlayerState joinedPlayerStateEntity = playerStateRepository.findById(joinedPlayer.getId()).orElseThrow(() -> new ResourceNotFoundException("Player", "id", joinedPlayer.getId()));
        if (joinedPlayerStateEntity.getBalanceAmount() < coinFlipGameDetails.getBetAmount()) {
            CoinFlipMatchErrorResponse coinFlipGameErrorEventResponse = CoinFlipMatchErrorResponse.builder().eventType(CoinFlipGameErrorEventResponse.COIN_FLIP_GAME_LOW_BALANCE.ordinal()).errorMessage("Joined User's current balance is low than the bet amount").build();
            this.simpMessagingTemplate.convertAndSend("/topic/coinflip", coinFlipGameErrorEventResponse);
            return true;
        }
        return false;
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
