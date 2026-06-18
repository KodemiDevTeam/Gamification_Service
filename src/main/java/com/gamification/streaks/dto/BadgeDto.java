package com.gamification.streaks.dto;

import com.gamification.streaks.enums.BadgeTier;
import com.gamification.streaks.enums.RewardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BadgeDto {
    private String badgeId;
    private String badgeName;
    private String badgeType;
    private String description;
    private String iconUrl;
    private Integer xpRequired;             // new: XP Required to earn the badge
    private Integer xpReward;               // new: XP Reward points given
    private BadgeTier badgeTier;            // new: Bronze, Silver, Gold, Platinum, Diamond
    private RewardType rewardType;          // new: Reward Type (defaults to XP)
    private String eligibilityRule;
    private Boolean active;
    private LocalDateTime createdAt;
}
