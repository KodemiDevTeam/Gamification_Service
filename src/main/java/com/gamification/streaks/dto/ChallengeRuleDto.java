package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeRuleDto {
    private String ruleId;
    private String challengeId;
    private Integer totalMarks;
    private Integer passMark;
    private Integer rewardCoins;
    private Integer rewardXp;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
}
