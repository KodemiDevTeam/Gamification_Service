package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "XpTransaction")
public class XpTransaction {
    @DynamoDBHashKey(attributeName = "xpTransactionId")
    private String xpTransactionId;
    @DynamoDBAttribute(attributeName ="userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "xpAmount")
    private Integer xpAmount;
    @DynamoDBAttribute(attributeName = "xpSource")
    private String xpSource;
    @DynamoDBAttribute(attributeName = "referenceId")
    private String referenceId;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;
}
