package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.gamification.streaks.config.LocalDateTimeConverter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "UserBadge")
public class UserBadge {
    @DynamoDBHashKey(attributeName = "userBadgeId")
    private String userBadgeId;
    @DynamoDBAttribute(attributeName="userId")
    private String userId;
    @DynamoDBAttribute(attributeName="badgeId")
    private String badgeId;
    @DynamoDBAttribute(attributeName="badgeName")
    private String badgeName;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName="createdData")
    private LocalDateTime createdData;
    @DynamoDBAttribute(attributeName="xpAwarded")
    private Integer xpAwarded;
}
