package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.StreakRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StreakRecordRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public StreakRecordRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }
    public StreakRecord save(StreakRecord streakRecord){
        dynamoDBMapper.save(streakRecord);
        return streakRecord;
    }
    public StreakRecord findById(String streakId) { return dynamoDBMapper.load(StreakRecord.class, streakId); }
    public List<StreakRecord> findAll() { return dynamoDBMapper.scan(StreakRecord.class, new DynamoDBScanExpression()); }
    public void delete(String streakId){
        StreakRecord streakRecord = dynamoDBMapper.load(StreakRecord.class, streakId);
        if(streakRecord != null){
            dynamoDBMapper.delete(streakRecord);
        }
    }
}
