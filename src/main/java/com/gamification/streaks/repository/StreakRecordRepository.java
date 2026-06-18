package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gamification.streaks.enums.StreakStatus;
import com.gamification.streaks.model.StreakRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /** Returns all streaks for a given user — used by Streaks Activity tab. */
    public List<StreakRecord> findByUserId(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(userId));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("userId = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(StreakRecord.class, scan);
    }

    /** Returns all active streaks — used for Dashboard "Active Streaks" metric card. */
    public List<StreakRecord> findByStatus(StreakStatus status) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(status.name()));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("streakStatus = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(StreakRecord.class, scan);
    }
}
