package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.UserBadge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserBadgeRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public UserBadgeRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }
    public UserBadge save(UserBadge userBadge){
        dynamoDBMapper.save(userBadge);
        return userBadge;
    }
    public UserBadge findById(String userBadgeId) { return dynamoDBMapper.load(UserBadge.class, userBadgeId); }
    public List<UserBadge> findAll() { return dynamoDBMapper.scan(UserBadge.class, new DynamoDBScanExpression()); }
    public void delete(String userBadgeId){
        UserBadge userBadge = dynamoDBMapper.load(UserBadge.class, userBadgeId);
        if(userBadge != null){
            dynamoDBMapper.delete(userBadge);
        }
    }

    /** Returns all badges for a given user. */
    public List<UserBadge> findByUserId(String userId) {
        java.util.Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> eav = new java.util.HashMap<>();
        eav.put(":v1", new com.amazonaws.services.dynamodbv2.model.AttributeValue().withS(userId));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("userId = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(UserBadge.class, scan);
    }
}
