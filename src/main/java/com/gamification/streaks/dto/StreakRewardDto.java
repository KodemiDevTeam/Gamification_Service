package com.gamification.streaks.dto;

import com.gamification.streaks.enums.RewardType;
import com.gamification.streaks.enums.StreakType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StreakRewardDto {
    private String streakRewardId;
    private String streakName;
    private StreakType streakType;      // new: Streak Type column in UI
    private Integer days;
    private RewardType rewardType;     // fixed: was raw String
    private Integer xpReward;
    private Integer coinReward;
    private Integer usersEarning;      // new: Users Earning column in UI
    private String applicableOn;
    private String couponExpiry;
    private Integer minRedemptionValue;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
