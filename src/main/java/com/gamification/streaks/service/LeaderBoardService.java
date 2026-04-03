package com.gamification.streaks.service;

import com.gamification.streaks.dto.LeaderBoardEntryDto;
import com.gamification.streaks.model.LeaderBoardEntry;

import java.util.List;

public interface LeaderBoardService {
    LeaderBoardEntry createLeaderBoard(LeaderBoardEntryDto leaderBoardEntryDto);
    String createLeaderBoard(LeaderBoardEntry leaderBoardEntry);
    List<LeaderBoardEntryDto> getAllLeaderBoard();
    String updateLeaderBoard(String leaderboardId,LeaderBoardEntry leaderBoardEntry);
    String deleteLeaderBoard(String leaderboardId);
}
