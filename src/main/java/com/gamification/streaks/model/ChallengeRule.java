package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "ChallengeRule")
public class ChallengeRule {

    @DynamoDBHashKey(attributeName = "ruleId")
    private String ruleId;

    @DynamoDBAttribute(attributeName = "challengeId")
    private String challengeId;

    @DynamoDBAttribute(attributeName = "totalMarks")
    private Integer totalMarks;

    @DynamoDBAttribute(attributeName = "passMark")
    private Integer passMark;

    @DynamoDBAttribute(attributeName = "rewardCoins")
    private Integer rewardCoins;

    @DynamoDBAttribute(attributeName = "rewardXp")
    private Integer rewardXp;

    @DynamoDBAttribute(attributeName = "active")
    private Boolean active;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private String updatedAt;
}
