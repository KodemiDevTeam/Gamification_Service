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
@DynamoDBTable(tableName = "LeaderBoardEntry")
public class LeaderBoardEntry {
    @DynamoDBHashKey(attributeName="LeaderboardId")
    private String leaderboardId;
    @DynamoDBAttribute(attributeName="LeaderBoardType")
    private String leaderboardType;
    @DynamoDBAttribute(attributeName="userId")
    private String userId;
    @DynamoDBAttribute(attributeName="XpScore")
    private Integer xpScore;
    @DynamoDBAttribute(attributeName="rank")
    private Integer rank;
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName="periodStart")
    private LocalDate periodStart;
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName="periodEnd")
    private LocalDate periodEnd;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName="updatedAt")
    private LocalDateTime updatedAt;
}
