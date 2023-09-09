package com.vasu.steelsungapi.games.infrastructure.adapters.input.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasu.steelsungapi.games.application.ports.input.CreateCoinFlipGameUseCase;
import com.vasu.steelsungapi.games.domain.model.CoinFlipMatchCreateWsRequest;
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
    public void broadcastNews(@Payload String message,Principal principal) throws JsonProcessingException {
        CoinFlipMatchCreateWsRequest coinFlipMatchCreateWsRequest=mapper.readValue(message,CoinFlipMatchCreateWsRequest.class);
        createCoinFlipGameUseCase.createCoinFlipGame(coinFlipMatchCreateWsRequest,principal.getName());
    }
}
