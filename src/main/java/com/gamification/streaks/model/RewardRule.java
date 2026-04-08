package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.gamification.streaks.config.LocalDateTimeConverter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@DynamoDBTable(tableName = "RewardRule")
public class RewardRule {
    @DynamoDBHashKey(attributeName = "ruleId")
    private String ruleId;
    @DynamoDBAttribute(attributeName="ruleName")
    private String ruleName;
    @DynamoDBAttribute(attributeName="ruleType")
    private String ruleType;
    @DynamoDBAttribute(attributeName="conditionType")
    private String conditionType;
    @DynamoDBAttribute(attributeName="conditionValue")
    private Integer conditionValue;
    @DynamoDBAttribute(attributeName="rewardType")
    private String rewardType;
    @DynamoDBAttribute(attributeName="rewardValue")
    private Integer rewardValue;
    @DynamoDBAttribute(attributeName="active")
    private Boolean active;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName="createdAt")
    private LocalDateTime createdAt;
}

