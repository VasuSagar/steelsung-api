package com.vasu.steelsungapi.player.infrastructure.adapters.input.rest;

import com.vasu.steelsungapi.player.application.ports.input.GetBalanceUseCase;
import com.vasu.steelsungapi.player.application.ports.input.GetPlayerStateUseCase;
import com.vasu.steelsungapi.player.domain.model.GetBalanceResponse;
import com.vasu.steelsungapi.player.domain.model.GetPlayerStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/player-state")
@RequiredArgsConstructor
public class PlayerStateRestAdapter {
    private final GetPlayerStateUseCase getPlayerStateUseCase;

    @GetMapping()
    public ResponseEntity<GetPlayerStateResponse> getPlayerState(){
        return new ResponseEntity<>(getPlayerStateUseCase.getPlayerState(), HttpStatus.OK);
    }
}
