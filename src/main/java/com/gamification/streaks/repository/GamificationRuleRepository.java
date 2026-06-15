package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.GamificationRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GamificationRuleRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public GamificationRuleRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }

    public GamificationRule save(GamificationRule gamificationRule){
        dynamoDBMapper.save(gamificationRule);
        return gamificationRule;
    }

    public GamificationRule findById(String ruleId) { return dynamoDBMapper.load(GamificationRule.class, ruleId); }

    public List<GamificationRule> findAll() { return dynamoDBMapper.scan(GamificationRule.class, new DynamoDBScanExpression()); }

    public void delete(String ruleId){
        GamificationRule rule = dynamoDBMapper.load(GamificationRule.class, ruleId);
        if(rule != null){
            dynamoDBMapper.delete(rule);
        }
    }
}