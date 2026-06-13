package com.gamification.streaks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserGamificationProfileDto {
    @NotBlank(message = "User ID is required")
    private String userId;

    private String adminId;

    @NotBlank(message = "Role is required")
    private String role;

    @NotNull(message = "Total XP is required")
    @Min(value = 0, message = "Total XP cannot be negative")
    private Integer totalXp;

    @NotNull(message = "Current level is required")
    @Min(value = 0, message = "Current level cannot be negative")
    private Integer currentLevel;

    @NotNull(message = "Longest streak is required")
    @Min(value = 0, message = "Longest streak cannot be negative")
    private Integer longestStreak;

    private LocalDate lastActivityData;

    @NotNull(message = "Total badges is required")
    @Min(value = 0, message = "Total badges cannot be negative")
    private Integer totalBadges;

    @NotNull(message = "Total reward is required")
    @Min(value = 0, message = "Total reward cannot be negative")
    private Integer totalReward;

    @NotNull(message = "Coin balance is required")
    @Min(value = 0, message = "Coin balance cannot be negative")
    private Integer coinBalance;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
