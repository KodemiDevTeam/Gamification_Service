package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.UserBadge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserBadgeRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public UserBadgeRepository(DynamoDBMapper dynamoDBMapper){
        this.dynamoDBMapper=dynamoDBMapper;
    }
    public UserBadge save(UserBadge userBadge){
        dynamoDBMapper.save(userBadge);
        return userBadge;
    }
    public  UserBadge findById(String userBadgeId){
        return dynamoDBMapper.load(UserBadge.class,userBadgeId);
    }
    public List<UserBadge> findAll(){
        return dynamoDBMapper.scan(UserBadge.class,new DynamoDBScanExpression());
    }
    public void delete(String userBadgeId){
        UserBadge userBadge=dynamoDBMapper.load(UserBadge.class,userBadgeId);
        if(userBadge!=null){
            dynamoDBMapper.delete(userBadge);
        }
    }
}
