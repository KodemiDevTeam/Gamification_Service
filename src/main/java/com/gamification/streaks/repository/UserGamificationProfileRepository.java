package com.gamification.streaks.repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.UserGamificationProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserGamificationProfileRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public UserGamificationProfileRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }
    public UserGamificationProfile save(UserGamificationProfile userGamificationProfile){
        dynamoDBMapper.save(userGamificationProfile);
        return userGamificationProfile;
    }
    public UserGamificationProfile findById(String userId) { return dynamoDBMapper.load(UserGamificationProfile.class, userId); }
    public List<UserGamificationProfile> findAll() { return dynamoDBMapper.scan(UserGamificationProfile.class, new DynamoDBScanExpression()); }
    public void delete(String userId){
        UserGamificationProfile userGamificationProfile = dynamoDBMapper.load(UserGamificationProfile.class, userId);
        if(userGamificationProfile != null){
            dynamoDBMapper.delete(userGamificationProfile);
        }
    }
}