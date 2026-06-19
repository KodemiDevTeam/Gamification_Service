package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gamification.streaks.model.ChallengeRule;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChallengeRuleRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public ChallengeRuleRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public ChallengeRule save(ChallengeRule rule) {
        dynamoDBMapper.save(rule);
        return rule;
    }

    public ChallengeRule findById(String ruleId) {
        return dynamoDBMapper.load(ChallengeRule.class, ruleId);
    }

    public List<ChallengeRule> findAll() {
        return dynamoDBMapper.scan(ChallengeRule.class, new DynamoDBScanExpression());
    }

    public void delete(String ruleId) {
        ChallengeRule rule = dynamoDBMapper.load(ChallengeRule.class, ruleId);
        if (rule != null) {
            dynamoDBMapper.delete(rule);
        }
    }

    public List<ChallengeRule> findByChallengeId(String challengeId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(challengeId));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("challengeId = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(ChallengeRule.class, scan);
    }
}
