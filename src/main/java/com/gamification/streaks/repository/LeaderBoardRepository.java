package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.LeaderBoardEntry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaderBoardRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public LeaderBoardRepository(DynamoDBMapper dynamoDBMapper){
        this.dynamoDBMapper=dynamoDBMapper;
    }
    public LeaderBoardEntry save(LeaderBoardEntry leaderBoardEntry){
        dynamoDBMapper.save(leaderBoardEntry);
        return leaderBoardEntry;
    }
    public LeaderBoardEntry findById(String leaderBoardId){
        return dynamoDBMapper.load(LeaderBoardEntry.class,leaderBoardId);
    }
    public List<LeaderBoardEntry> findAll(){
        return dynamoDBMapper.scan(LeaderBoardEntry.class,new DynamoDBScanExpression());
    }
    public void delete(String leaderBoardId){
        LeaderBoardEntry leaderBoardEntry=dynamoDBMapper.load(LeaderBoardEntry.class,leaderBoardId);
        if(leaderBoardEntry!=null){
            dynamoDBMapper.delete(leaderBoardEntry);
        }
    }
}
