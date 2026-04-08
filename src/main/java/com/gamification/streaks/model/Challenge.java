package com.gamification.streaks.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.gamification.streaks.config.LocalDateConverter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@DynamoDBTable(tableName = "Challenge")
public class Challenge {
    @DynamoDBHashKey(attributeName = "challengeId")
    private String challengeId;
    @DynamoDBAttribute(attributeName="challengeName")
    private String challengeName;
    @DynamoDBAttribute(attributeName="challengeType")
    private String challengeType;
    @DynamoDBAttribute(attributeName="Description")
    private String description;
    @DynamoDBAttribute(attributeName="targetType")
    private String targetType;
    @DynamoDBAttribute(attributeName="targetCount")
    private Integer targetCount;
    @DynamoDBAttribute(attributeName="rewardXP")
    private Integer rewardXP;
    @DynamoDBAttribute(attributeName="rewardBadge")
    private String rewardBadge;
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName="startData")
    private LocalDate startDate;
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    @DynamoDBAttribute(attributeName="endDate")
    private LocalDate endDate;
    @DynamoDBAttribute(attributeName="Active")
    private Boolean active;
}
