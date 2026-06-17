package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardTierDto {
    private String tierName;
    private Integer minScore;
    private Integer rewardPoints;
    private String rewardBadge;
}
