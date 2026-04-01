package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "GamificationRule")
public class GamificationRule {

    @DynamoDBHashKey(attributeName = "RuleId")
    private String ruleId;

    @DynamoDBAttribute(attributeName = "RuleName")
    private String ruleName;

    @DynamoDBAttribute(attributeName = "RuleType")
    private String ruleType;

    @DynamoDBAttribute(attributeName = "Config")
    private String config;

    @DynamoDBAttribute(attributeName = "Enabled")
    private Boolean enabled;

    @DynamoDBAttribute(attributeName = "Version")
    private Integer version;

    @DynamoDBAttribute(attributeName = "AbCohort")
    private String abCohort;

    @DynamoDBAttribute(attributeName = "CreatedAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "UpdatedAt")
    private String updatedAt;

    @DynamoDBAttribute(attributeName = "UpdatedBy")
    private String updatedBy;

    @DynamoDBAttribute(attributeName = "RollbackFromRuleId")
    private String rollbackFromRuleId;
}
