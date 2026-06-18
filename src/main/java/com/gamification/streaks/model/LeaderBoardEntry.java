package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gamification.streaks.enums.LeaderBoardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "LeaderBoardEntry")
public class LeaderBoardEntry {
    @DynamoDBHashKey(attributeName = "leaderboardId")
    private String leaderboardId;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "leaderboardType")
    private LeaderBoardType leaderboardType;   // fixed: was raw String
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "userName")
    private String userName;                   // new: for Dashboard Top Leaderboard display
    @DynamoDBAttribute(attributeName = "userAvatarUrl")
    private String userAvatarUrl;              // new: for Dashboard avatar display
    @DynamoDBAttribute(attributeName = "xpScore")
    private Integer xpScore;
    @DynamoDBAttribute(attributeName = "rank")
    private Integer rank;
    @DynamoDBAttribute(attributeName = "periodStart")
    private LocalDate periodStart;
    @DynamoDBAttribute(attributeName = "periodEnd")
    private LocalDate periodEnd;
    @DynamoDBAttribute(attributeName = "updatedAt")
    private LocalDateTime updatedAt;
}
