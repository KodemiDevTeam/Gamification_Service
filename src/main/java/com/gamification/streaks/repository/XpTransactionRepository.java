package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.XpTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class XpTransactionRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public XpTransactionRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }
    public XpTransaction save(XpTransaction xpTransaction){
        dynamoDBMapper.save(xpTransaction);
        return xpTransaction;
    }
    public XpTransaction findById(String xpTransactionId) { return dynamoDBMapper.load(XpTransaction.class, xpTransactionId); }
    public List<XpTransaction> findAll() { return dynamoDBMapper.scan(XpTransaction.class, new DynamoDBScanExpression()); }
    public void delete(String xpTransactionId){
        XpTransaction xpTransaction = dynamoDBMapper.load(XpTransaction.class, xpTransactionId);
        if(xpTransaction != null){
            dynamoDBMapper.delete(xpTransaction);
        }
    }

    /** Returns all XP transactions for a given user. */
    public List<XpTransaction> findByUserId(String userId) {
        java.util.Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> eav = new java.util.HashMap<>();
        eav.put(":v1", new com.amazonaws.services.dynamodbv2.model.AttributeValue().withS(userId));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("userId = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(XpTransaction.class, scan);
    }
}
