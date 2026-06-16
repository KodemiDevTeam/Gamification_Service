package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "StreakReward")
public class StreakReward {
    @DynamoDBHashKey(attributeName = "streakRewardId")
    private String streakRewardId;

    @DynamoDBAttribute(attributeName = "streakName")
    private String streakName;

    @DynamoDBAttribute(attributeName = "days")
    private Integer days;

    @DynamoDBAttribute(attributeName = "rewardType")
    private String rewardType;

    @DynamoDBAttribute(attributeName = "xpReward")
    private Integer xpReward;

    @DynamoDBAttribute(attributeName = "coinReward")
    private Integer coinReward;

    @DynamoDBAttribute(attributeName = "applicableOn")
    private String applicableOn;

    @DynamoDBAttribute(attributeName = "couponExpiry")
    private String couponExpiry;

    @DynamoDBAttribute(attributeName = "minRedemptionValue")
    private Integer minRedemptionValue;

    @DynamoDBAttribute(attributeName = "active")
    private Boolean active;

    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private LocalDateTime updatedAt;
}
