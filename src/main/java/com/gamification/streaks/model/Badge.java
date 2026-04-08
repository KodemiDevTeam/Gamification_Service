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
@DynamoDBTable(tableName = "Badge")
public class Badge {
    @DynamoDBHashKey(attributeName = "badgeId")
    private String badgeId;
    @DynamoDBAttribute(attributeName="badgeName")
    private String badgeName;
    @DynamoDBAttribute(attributeName="badgeType")
    private String badgeType;
    @DynamoDBAttribute(attributeName="description")
    private String description;
    @DynamoDBAttribute(attributeName="iconUrl")
    private String iconUrl;
    @DynamoDBAttribute(attributeName="xpReward")
    private Integer xpReward;
    @DynamoDBAttribute(attributeName = "eligibilityRule")
    private String eligibilityRule;
    @DynamoDBAttribute(attributeName="active")
    private Boolean active;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName="createdAt")
    private LocalDateTime createdAt;
}
