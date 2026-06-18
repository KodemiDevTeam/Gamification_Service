package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gamification.streaks.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "UserGamificationProfile")
public class UserGamificationProfile {
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "role")
    private UserRole role;
    @DynamoDBAttribute(attributeName = "totalXp")
    private Integer totalXp;
    @DynamoDBAttribute(attributeName = "currentLevel")
    private Integer currentLevel;
    @DynamoDBAttribute(attributeName = "currentStreak")
    private Integer currentStreak;
    @DynamoDBAttribute(attributeName = "longestStreak")
    private Integer longestStreak;
    @DynamoDBAttribute(attributeName = "lastActivityDate")  // fixed: was lastActivityData
    private LocalDate lastActivityDate;
    @DynamoDBAttribute(attributeName = "totalBadges")
    private Integer totalBadges;
    @DynamoDBAttribute(attributeName = "totalReward")
    private Integer totalReward;
    @DynamoDBAttribute(attributeName = "coinBalance")
    private Integer coinBalance;
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;
    @DynamoDBAttribute(attributeName = "updatedAt")
    private LocalDateTime updatedAt;
}
