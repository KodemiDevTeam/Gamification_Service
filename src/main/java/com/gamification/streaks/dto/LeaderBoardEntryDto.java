package com.gamification.streaks.dto;
import com.gamification.streaks.enums.LeaderBoardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class LeaderBoardEntryDto {
    private String leaderboardId;
    private LeaderBoardType leaderboardType;  // fixed: was raw String
    private String userId;
    private String userName;                  // new: Dashboard display name
    private String userAvatarUrl;             // new: Dashboard avatar
    private Integer xpScore;
    private Integer rank;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDateTime updatedAt;
}
