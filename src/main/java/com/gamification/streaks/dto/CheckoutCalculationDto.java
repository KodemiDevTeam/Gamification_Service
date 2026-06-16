package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutCalculationDto {
    private String userId;
    private Integer totalXp;
    private Integer coinBalance;
    private Integer potentialCoinsFromXp;
    private Double discountValue;
}
