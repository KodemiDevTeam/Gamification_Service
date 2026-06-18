package com.gamification.streaks.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.gamification.streaks.enums.XpSource;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "XpTransaction")
public class XpTransaction {
    @DynamoDBHashKey(attributeName = "xpTransactionId")
    private String xpTransactionId;
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "xpAmount")
    private Integer xpAmount;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "xpSource")
    private XpSource xpSource;           // fixed: was raw String
    @DynamoDBAttribute(attributeName = "referenceId")
    private String referenceId;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;     // fixed: was String
}
