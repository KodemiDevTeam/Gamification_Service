package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.Challenge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChallengeRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public ChallengeRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }
    public Challenge save(Challenge challenge){
        dynamoDBMapper.save(challenge);
        return challenge;
    }
    public Challenge findById(String challengeId) { return dynamoDBMapper.load(Challenge.class, challengeId); }
    public List<Challenge> findAll() { return dynamoDBMapper.scan(Challenge.class, new DynamoDBScanExpression()); }
    public void delete(String challengeId){
        Challenge challenge = dynamoDBMapper.load(Challenge.class, challengeId);
        if(challenge != null){
            dynamoDBMapper.delete(challenge);
        }
    }
}
