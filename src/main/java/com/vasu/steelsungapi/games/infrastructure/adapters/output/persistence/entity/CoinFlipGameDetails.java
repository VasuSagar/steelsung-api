package com.vasu.steelsungapi.games.infrastructure.adapters.output.persistence.entity;

import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinFlipGameDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
    private Double betAmount;
    private Integer creatorCoinSide;
    private Integer participantCoinSide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private User participantUser;
}
