package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBDocument
public class RewardTier {

    @DynamoDBAttribute(attributeName = "tierName")
    private String tierName;

    @DynamoDBAttribute(attributeName = "minScore")
    private Integer minScore;

    @DynamoDBAttribute(attributeName = "rewardPoints")
    private Integer rewardPoints;

    @DynamoDBAttribute(attributeName = "rewardBadge")
    private String rewardBadge;
}
