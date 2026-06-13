package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class LeaderBoardEntryDto {
    private String leaderboardId;
    private String leaderboardType;
    private String userId;
    private Integer xpScore;
    private Integer rank;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDateTime updatedAt;
}
