package com.gamification.streaks.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RewardRuleDto {
    private String ruleId;

    @NotBlank(message = "Rule name is required")
    private String ruleName;

    @NotBlank(message = "Rule type is required")
    private String ruleType;

    @NotBlank(message = "Condition type is required")
    private String conditionType;

    @NotNull(message = "Condition value is required")
    @Min(value = 0, message = "Condition value cannot be negative")
    private Integer conditionValue;

    @NotBlank(message = "Reward type is required")
    private String rewardType;

    @NotNull(message = "Reward value is required")
    @Min(value = 0, message = "Reward value cannot be negative")
    private Integer rewardValue;

    @NotNull(message = "Active flag is required")
    private Boolean active;

    private LocalDateTime createdAt;
}
