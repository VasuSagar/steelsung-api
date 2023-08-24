package com.vasu.steelsungapi.player.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPlayerStateResponse {
    private String username;
    private Double currentBalanceAmount;
    private Integer level;
    private Long experience;
    private Long totalBetAmount;
}
