package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChallengePerformanceDto {
    private String performanceId;
    private String userId;
    private String challengeId;
    private Integer score;
    private Boolean passed;
    private Integer coinsEarned;
    private Integer xpEarned;
    private String performedAt;
}
