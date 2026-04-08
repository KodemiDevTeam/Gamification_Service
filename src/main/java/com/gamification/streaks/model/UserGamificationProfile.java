package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.gamification.streaks.config.LocalDateConverter;
import com.gamification.streaks.config.LocalDateTimeConverter;
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
    @DynamoDBAttribute(attributeName = "adminId")
    private String role;
    @DynamoDBAttribute(attributeName = "totalXp")
    private Integer totalXp;
    @DynamoDBAttribute(attributeName = "currentLevel")
    private Integer currentLevel;
    @DynamoDBAttribute(attributeName = "longestStreak")
    private Integer longestStreak;
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName = "lastActivityData")
    private LocalDate lastActivityData;
    @DynamoDBAttribute(attributeName = "totalBadges")
    private Integer totalBadges;
    @DynamoDBAttribute(attributeName = "totalReward")
    private Integer totalReward;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "updatedAt")
    private LocalDateTime updatedAt;
}
