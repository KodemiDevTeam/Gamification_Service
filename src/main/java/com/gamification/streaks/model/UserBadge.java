package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "UserBadge")
public class UserBadge {
    @DynamoDBHashKey(attributeName = "userBadgeId")
    private String userBadgeId;
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "badgeId")
    private String badgeId;
    @DynamoDBAttribute(attributeName = "badgeName")
    private String badgeName;
    @DynamoDBAttribute(attributeName = "badgeIconUrl")
    private String badgeIconUrl;
    @DynamoDBAttribute(attributeName = "xpAwarded")
    private Integer xpAwarded;
    @DynamoDBAttribute(attributeName = "awardedBy")
    private String awardedBy;              // "SYSTEM" or admin userId
    @DynamoDBAttribute(attributeName = "createdAt")   // fixed: was createdData
    private LocalDateTime createdAt;
    @DynamoDBAttribute(attributeName = "revokedAt")
    private LocalDateTime revokedAt;
    @DynamoDBAttribute(attributeName = "revokeReason")
    private String revokeReason;
}
