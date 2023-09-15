package com.vasu.steelsungapi.games.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasu.steelsungapi.games.application.ports.input.CreateCoinFlipGameUseCase;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;
import com.vasu.steelsungapi.games.domain.model.CoinFlipWebsocketRequest;
import com.vasu.steelsungapi.games.domain.util.CoinFlipGameRequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class GamesRestAdapter {
    private final CreateCoinFlipGameUseCase createCoinFlipGameUseCase;
    ObjectMapper mapper = new ObjectMapper();

    @MessageMapping("/coinflip")
    public void broadcastNews(@Payload String message, Principal principal) throws JsonProcessingException {
        CoinFlipWebsocketRequest coinFlipWebsocketRequest = mapper.readValue(message, CoinFlipWebsocketRequest.class);
        if (coinFlipWebsocketRequest.getCoinFlipRequestType() == CoinFlipGameRequestType.CREATE_COIN_FLIP_GAME) {
            createCoinFlipGameUseCase.createCoinFlipGame(coinFlipWebsocketRequest.getCoinFlipMatchCreateWsRequest(), principal.getName());
        }
    }
}
