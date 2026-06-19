package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "UserChallengePerformance")
public class UserChallengePerformance {

    @DynamoDBHashKey(attributeName = "performanceId")
    private String performanceId;

    @DynamoDBAttribute(attributeName = "userId")
    private String userId;

    @DynamoDBAttribute(attributeName = "challengeId")
    private String challengeId;

    @DynamoDBAttribute(attributeName = "score")
    private Integer score;

    @DynamoDBAttribute(attributeName = "passed")
    private Boolean passed;

    @DynamoDBAttribute(attributeName = "coinsEarned")
    private Integer coinsEarned;

    @DynamoDBAttribute(attributeName = "xpEarned")
    private Integer xpEarned;

    @DynamoDBAttribute(attributeName = "performedAt")
    private String performedAt;
}
