package com.gamification.streaks.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamificationRuleDto {
    private String ruleId;

    @NotBlank(message = "Rule name is required")
    private String ruleName;

    @NotBlank(message = "Rule type is required")
    private String ruleType;

    @NotBlank(message = "Config is required")
    private String config;

    @NotNull(message = "Enabled flag is required")
    private Boolean enabled;

    @NotNull(message = "Version is required")
    @Min(value = 1, message = "Version must be at least 1")
    private Integer version;

    private String abCohort;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
    private String rollbackFromRuleId;
}
