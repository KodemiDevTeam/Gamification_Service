package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.StreakReward;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StreakRewardRepository {
    private final DynamoDBMapper dynamoDBMapper;

    public StreakRewardRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public StreakReward save(StreakReward streakReward) {
        dynamoDBMapper.save(streakReward);
        return streakReward;
    }

    public StreakReward findById(String id) {
        return dynamoDBMapper.load(StreakReward.class, id);
    }

    public List<StreakReward> findAll() {
        return dynamoDBMapper.scan(StreakReward.class, new DynamoDBScanExpression());
    }

    public void delete(String id) {
        StreakReward reward = dynamoDBMapper.load(StreakReward.class, id);
        if (reward != null) {
            dynamoDBMapper.delete(reward);
        }
    }
}
