package com.vasu.steelsungapi.games.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinFlipMatchErrorResponse {
    private Integer eventType;
    private String errorMessage;
}
