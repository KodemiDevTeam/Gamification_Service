package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gamification.streaks.enums.BadgeTier;
import com.gamification.streaks.enums.RewardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "Badge")
public class Badge {
    @DynamoDBHashKey(attributeName = "badgeId")
    private String badgeId;
    
    @DynamoDBAttribute(attributeName = "badgeName")
    private String badgeName;
    
    @DynamoDBAttribute(attributeName = "badgeType")
    private String badgeType;
    
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    
    @DynamoDBAttribute(attributeName = "iconUrl")
    private String iconUrl;
    
    @DynamoDBAttribute(attributeName = "xpRequired")
    private Integer xpRequired;             // new: XP Required to earn the badge (from Create/Edit form)
    
    @DynamoDBAttribute(attributeName = "xpReward")
    private Integer xpReward;               // new: XP Reward points given on badge earning
    
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "badgeTier")
    private BadgeTier badgeTier;            // new: Bronze, Silver, Gold, Platinum, Diamond
    
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "rewardType")
    private RewardType rewardType;          // new: Reward Type (XP, COINS, etc. - default XP)
    
    @DynamoDBAttribute(attributeName = "eligibilityRule")
    private String eligibilityRule;
    
    @DynamoDBAttribute(attributeName = "active")
    private Boolean active;
    
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;
}
