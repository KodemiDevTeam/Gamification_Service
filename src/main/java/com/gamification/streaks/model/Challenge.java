package com.gamification.streaks.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @DynamoDBAttribute(attributeName="description")
    private String description;
    @DynamoDBAttribute(attributeName="targetType")
    private String targetType;
    @DynamoDBAttribute(attributeName="targetCount")
    private Integer targetCount;
    @DynamoDBAttribute(attributeName="rewardXP")
    private Integer rewardXP;
    @DynamoDBAttribute(attributeName="rewardBadge")
    private String rewardBadge;
    @DynamoDBAttribute(attributeName="startDate")
    private LocalDate startDate;
    @DynamoDBAttribute(attributeName="endDate")
    private LocalDate endDate;
    @DynamoDBAttribute(attributeName="active")
    private Boolean active;
    @DynamoDBAttribute(attributeName="userId")
    private String userId;
    @DynamoDBAttribute(attributeName="transactionId")
    private String transactionId;
    @DynamoDBAttribute(attributeName="maxParticipants")
    private Integer maxParticipants;
    @DynamoDBAttribute(attributeName="currentParticipants")
    private Integer currentParticipants;
    @DynamoDBAttribute(attributeName="rewardTiers")
    private List<RewardTier> rewardTiers;
}
