package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserGamificationProfileDto {
    private String userId;
    private String adminId;
    private String role;
    private Integer totalXp;
    private Integer currentLevel;
    private Integer longestStreak;
    private LocalDate lastActivityData;
    private Integer totalBadges;
    private Integer totalReward;
    private Integer coinBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
