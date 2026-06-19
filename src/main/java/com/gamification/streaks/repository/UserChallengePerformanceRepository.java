package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gamification.streaks.model.UserChallengePerformance;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserChallengePerformanceRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public UserChallengePerformanceRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public UserChallengePerformance save(UserChallengePerformance performance) {
        dynamoDBMapper.save(performance);
        return performance;
    }

    public UserChallengePerformance findById(String performanceId) {
        return dynamoDBMapper.load(UserChallengePerformance.class, performanceId);
    }

    public List<UserChallengePerformance> findAll() {
        return dynamoDBMapper.scan(UserChallengePerformance.class, new DynamoDBScanExpression());
    }

    public void delete(String performanceId) {
        UserChallengePerformance performance = dynamoDBMapper.load(UserChallengePerformance.class, performanceId);
        if (performance != null) {
            dynamoDBMapper.delete(performance);
        }
    }

    public List<UserChallengePerformance> findByUserId(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(userId));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("userId = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(UserChallengePerformance.class, scan);
    }

    public List<UserChallengePerformance> findByChallengeId(String challengeId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(challengeId));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("challengeId = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(UserChallengePerformance.class, scan);
    }
}
