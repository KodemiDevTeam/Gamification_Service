package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "GamificationRule")
public class GamificationRule {

    @DynamoDBHashKey(attributeName = "ruleId")
    private String ruleId;

    @DynamoDBAttribute(attributeName = "ruleName")
    private String ruleName;

    @DynamoDBAttribute(attributeName = "ruleType")
    private String ruleType;

    @DynamoDBAttribute(attributeName = "config")
    private String config;

    @DynamoDBAttribute(attributeName = "enabled")
    private Boolean enabled;

    @DynamoDBAttribute(attributeName = "version")
    private Integer version;

    @DynamoDBAttribute(attributeName = "abCohort")
    private String abCohort;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private String updatedAt;

    @DynamoDBAttribute(attributeName = "updatedBy")
    private String updatedBy;

    @DynamoDBAttribute(attributeName = "rollbackFromRuleId")
    private String rollbackFromRuleId;
}
