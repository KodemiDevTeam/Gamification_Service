package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamificationRuleDto {
    private String ruleId;
    private String ruleName;
    private String ruleType;
    private String config;
    private String conditionExpression;
    private Boolean enabled;
    private Integer version;
    private String abCohort;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
    private String rollbackFromRuleId;
}
