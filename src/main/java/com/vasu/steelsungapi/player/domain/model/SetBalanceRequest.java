package com.vasu.steelsungapi.player.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetBalanceRequest {
    @NotNull(message = "balance must not be empty")
    @Range(min = 10, max = 10000,message = "balance should be between 10-10000")
    private Double balanceAmount;
}
