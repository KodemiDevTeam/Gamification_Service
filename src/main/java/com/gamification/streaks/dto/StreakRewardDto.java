package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StreakRewardDto {
    private String streakRewardId;
    private String streakName;
    private Integer days;
    private String rewardType;
    private Integer xpReward;
    private Integer coinReward;
    private String applicableOn;
    private String couponExpiry;
    private Integer minRedemptionValue;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
