package com.vasu.steelsungapi.player.infrastructure.adapters.input.rest;

import com.vasu.steelsungapi.player.application.ports.input.GetBalanceUseCase;
import com.vasu.steelsungapi.player.application.ports.input.SetBalanceUseCase;
import com.vasu.steelsungapi.player.domain.model.GetBalanceResponse;
import com.vasu.steelsungapi.player.domain.model.SetBalanceRequest;
import com.vasu.steelsungapi.security.domain.model.LoginRequest;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class BalanceRestAdapter {
    private final GetBalanceUseCase getBalanceUseCase;
    private final SetBalanceUseCase setBalanceUseCase;

    @GetMapping()
    public ResponseEntity<GetBalanceResponse> getBalance(){
        return new ResponseEntity<>(getBalanceUseCase.getBalance(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<GetBalanceResponse> setBalance(@AuthenticationPrincipal User user, @Valid @RequestBody SetBalanceRequest setBalanceRequest) throws ParseException {
        return new ResponseEntity<>(setBalanceUseCase.setBalance(setBalanceRequest,user), HttpStatus.OK);
    }
}
