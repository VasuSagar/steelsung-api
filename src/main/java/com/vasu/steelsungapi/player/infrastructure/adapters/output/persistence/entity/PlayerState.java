package com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.entity;

import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerState {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    private Double balanceAmount=0.0;
    private Integer level=0;
    private Long experience=0L;
    private Long totalBetAmount=0L;
}
