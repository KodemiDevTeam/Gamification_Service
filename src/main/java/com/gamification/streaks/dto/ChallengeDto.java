package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChallengeDto {
    private String challengeId;
    private String challengeName;
    private String challengeType;
    private String description;
    private String targetType;
    private Integer targetCount;
    private Integer rewardXP;
    private String rewardBadge;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
}
