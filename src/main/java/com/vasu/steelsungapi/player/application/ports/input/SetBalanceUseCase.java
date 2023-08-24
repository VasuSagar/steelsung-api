package com.vasu.steelsungapi.player.application.ports.input;

import com.vasu.steelsungapi.player.domain.model.GetBalanceResponse;
import com.vasu.steelsungapi.player.domain.model.SetBalanceRequest;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;

import java.text.ParseException;

public interface SetBalanceUseCase {
    GetBalanceResponse setBalance(SetBalanceRequest setBalanceRequest, User user) throws ParseException;
}
