package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RewardRuleDto {
    private String ruleId;
    private String ruleName;
    private String ruleType;
    private String conditionType;
    private Integer conditionValue;
    private String rewardType;
    private Integer rewardValue;
    private Boolean active;
    private LocalDateTime createdAt;
}
