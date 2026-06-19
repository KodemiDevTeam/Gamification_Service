package com.gamification.streaks.dto;

import com.gamification.streaks.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserGamificationProfileDto {
    private String userId;
    private UserRole role;
    private Integer totalXp;
    private Integer currentLevel;
    private Integer currentStreak;
    private Integer longestStreak;
    private LocalDate lastActivityDate;   // fixed: was lastActivityData
    private Integer totalBadges;
    private Integer totalReward;
    private Integer coinBalance;
    private Integer lifetimeXp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
