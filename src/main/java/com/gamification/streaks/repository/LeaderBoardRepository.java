package com.gamification.streaks.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gamification.streaks.model.LeaderBoardEntry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaderBoardRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public LeaderBoardRepository(DynamoDBMapper dynamoDBMapper) { this.dynamoDBMapper = dynamoDBMapper; }
    public LeaderBoardEntry save(LeaderBoardEntry leaderBoardEntry){
        dynamoDBMapper.save(leaderBoardEntry);
        return leaderBoardEntry;
    }
    public LeaderBoardEntry findById(String leaderBoardId) { return dynamoDBMapper.load(LeaderBoardEntry.class, leaderBoardId); }
    public List<LeaderBoardEntry> findAll() { return dynamoDBMapper.scan(LeaderBoardEntry.class, new DynamoDBScanExpression()); }
    public void delete(String leaderBoardId){
        LeaderBoardEntry leaderBoardEntry = dynamoDBMapper.load(LeaderBoardEntry.class, leaderBoardId);
        if(leaderBoardEntry != null){
            dynamoDBMapper.delete(leaderBoardEntry);
        }
    }

    /** Returns entries for a specific leaderboard type. */
    public List<LeaderBoardEntry> findByLeaderboardType(com.gamification.streaks.enums.LeaderBoardType type) {
        java.util.Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> eav = new java.util.HashMap<>();
        eav.put(":v1", new com.amazonaws.services.dynamodbv2.model.AttributeValue().withS(type.name()));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("leaderboardType = :v1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(LeaderBoardEntry.class, scan);
    }

    /** Returns top N entries sorted by xpScore descending. */
    public List<LeaderBoardEntry> findTopN(int n) {
        List<LeaderBoardEntry> all = findAll();
        all.sort((a, b) -> {
            int scoreA = a.getXpScore() != null ? a.getXpScore() : 0;
            int scoreB = b.getXpScore() != null ? b.getXpScore() : 0;
            return Integer.compare(scoreB, scoreA); // descending
        });
        if (all.size() > n) {
            return all.subList(0, n);
        }
        return all;
    }
}
