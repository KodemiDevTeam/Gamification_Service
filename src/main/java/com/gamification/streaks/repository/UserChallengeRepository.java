package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gamification.streaks.model.UserChallenge;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserChallengeRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public UserChallengeRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public UserChallenge save(UserChallenge userChallenge) {
        dynamoDBMapper.save(userChallenge);
        return userChallenge;
    }

    public UserChallenge findById(String userChallengeId) {
        return dynamoDBMapper.load(UserChallenge.class, userChallengeId);
    }

    public List<UserChallenge> findAll() {
        return dynamoDBMapper.scan(UserChallenge.class, new DynamoDBScanExpression());
    }

    public void delete(String userChallengeId) {
        UserChallenge userChallenge = dynamoDBMapper.load(UserChallenge.class, userChallengeId);
        if (userChallenge != null) {
            dynamoDBMapper.delete(userChallenge);
        }
    }

    public List<UserChallenge> findByUserId(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :v1")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(UserChallenge.class, scanExpression);
    }

    public UserChallenge findByUserIdAndChallengeId(String userId, String challengeId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(userId));
        eav.put(":v2", new AttributeValue().withS(challengeId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :v1 and challengeId = :v2")
                .withExpressionAttributeValues(eav);

        List<UserChallenge> results = dynamoDBMapper.scan(UserChallenge.class, scanExpression);
        return results.isEmpty() ? null : results.get(0);
    }
}
