package com.vasu.steelsungapi.player.infrastructure.adapters.input.rest;

import com.vasu.steelsungapi.player.application.ports.input.GetBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class BalanceRestAdapter {
    private final GetBalanceUseCase getBalanceUseCase;

    @GetMapping()
    public ResponseEntity<Double> getBalance(){
        return new ResponseEntity<>(getBalanceUseCase.getBalance(), HttpStatus.OK);
    }
}
