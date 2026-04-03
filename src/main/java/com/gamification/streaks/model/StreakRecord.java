package com.gamification.streaks.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName ="StreakRecord")
public class StreakRecord {
    @DynamoDBHashKey(attributeName = "streakId")
    private String streakId;
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "streakType")
    private String streakType;
    @DynamoDBAttribute(attributeName = "currentStreakCount")
    private Integer currentStreakCount;
    @DynamoDBAttribute(attributeName = "longestStreakCount")
    private Integer longestStreakCount;
    @DynamoDBAttribute(attributeName = "lastActiveData")
    private LocalDate lastActiveData;
    @DynamoDBAttribute(attributeName = "streakStatus")
    private String streakStatus;
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;
    @DynamoDBAttribute(attributeName = "updatedAt")
    private LocalDateTime updatedAt;
}
