package com.gamification.streaks.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gamification.streaks.enums.StreakStatus;
import com.gamification.streaks.enums.StreakType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "StreakRecord")
public class StreakRecord {
    @DynamoDBHashKey(attributeName = "streakId")
    private String streakId;
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "streakType")
    private StreakType streakType;
    @DynamoDBAttribute(attributeName = "currentStreakCount")
    private Integer currentStreakCount;
    @DynamoDBAttribute(attributeName = "longestStreakCount")
    private Integer longestStreakCount;
    @DynamoDBAttribute(attributeName = "lastActiveDate")   // fixed: was lastActiveData
    private LocalDate lastActiveDate;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "streakStatus")
    private StreakStatus streakStatus;
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;
    @DynamoDBAttribute(attributeName = "updatedAt")
    private LocalDateTime updatedAt;
}
