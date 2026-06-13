package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.Badge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BadgeRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public BadgeRepository(DynamoDBMapper dynamoDBMapper){
        this.dynamoDBMapper=dynamoDBMapper;
    }
    public Badge save(Badge badge){
        dynamoDBMapper.save(badge);
        return badge;
    }
    public Badge findById(String badgeId){
        return dynamoDBMapper.load(Badge.class,badgeId);
    }
    public List<Badge> findALl(){
        return dynamoDBMapper.scan(Badge.class,new DynamoDBScanExpression());
    }
    public void delete(String badgeId){
        Badge badge=dynamoDBMapper.load(Badge.class,badgeId);
        if(badgeId!=null){
            dynamoDBMapper.delete(badge);
        }
    }
}
