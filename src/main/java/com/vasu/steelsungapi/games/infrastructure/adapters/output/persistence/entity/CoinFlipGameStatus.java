package com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinFlipGameStatus {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private CoinFlipGameDetails coinFlipGameDetails;

    private Integer gameStatus;
    private Long winnerId;
    private Long gameCreatedAt;
    private Long gameParticipantJoinedAt;
    private Long gameFinishedAt;
}
