package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "UserChallenge")
public class UserChallenge {

    @DynamoDBHashKey(attributeName = "userChallengeId")
    private String userChallengeId;

    @DynamoDBAttribute(attributeName = "userId")
    private String userId;

    @DynamoDBAttribute(attributeName = "challengeId")
    private String challengeId;

    @DynamoDBAttribute(attributeName = "status")
    private String status;

    @DynamoDBAttribute(attributeName = "progress")
    private Integer progress;

    @DynamoDBAttribute(attributeName = "joinedAt")
    private LocalDateTime joinedAt;

    @DynamoDBAttribute(attributeName = "completedAt")
    private LocalDateTime completedAt;
}
