package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.RewardRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RewardRuleRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public RewardRuleRepository(DynamoDBMapper dynamoDBMapper){
        this.dynamoDBMapper=dynamoDBMapper;
    }
    public RewardRule save(RewardRule rewardRule){
        dynamoDBMapper.save(rewardRule);
        return  rewardRule;
    }
    public RewardRule findById(String ruleId){
        return  dynamoDBMapper.load(RewardRule.class,ruleId);
    }
    public List<RewardRule> findAll(){
        return dynamoDBMapper.scan(RewardRule.class,new DynamoDBScanExpression());
    }
    public void delete(String ruleId){
        RewardRule rewardRule=dynamoDBMapper.load(RewardRule.class,ruleId);
        if(rewardRule!=null){
            dynamoDBMapper.delete(rewardRule);
        }
    }
}
