package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Powers the Dashboard overview page metrics and widgets.
 * Returned by GET /api/dashboard/summary.
 */
@Getter
@Setter
public class DashboardSummaryDto {
    private Integer leaderboardCount;
    private Integer totalBadges;
    private Integer totalChallenges;
    private Integer activeChallenges;
    private Integer endingSoonChallenges;
    private Integer expiredChallenges;
    private Integer totalRules;
    private Integer totalStreakRewards;
    private Integer userProfileCount;
    private Long totalXpIssued;
    private Integer activeStreaks;

    /** Top 10 leaderboard for the Dashboard widget */
    private List<LeaderBoardEntryDto> topLeaderboard;
}
