package com.gamification.streaks.dto;
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
    private Integer xpReward;
    private String eligibilityRule;
    private Boolean active;
    private LocalDateTime createdAt;
}
