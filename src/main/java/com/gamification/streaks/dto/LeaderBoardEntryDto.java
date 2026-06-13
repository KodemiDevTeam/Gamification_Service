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
public class LeaderBoardEntryDto {
    private String leaderboardId;

    @NotBlank(message = "Leaderboard type is required")
    private String leaderboardType;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "XP score is required")
    @Min(value = 0, message = "XP score cannot be negative")
    private Integer xpScore;

    @NotNull(message = "Rank is required")
    @Min(value = 1, message = "Rank must be at least 1")
    private Integer rank;

    @NotNull(message = "Period start date is required")
    private LocalDate periodStart;

    @NotNull(message = "Period end date is required")
    private LocalDate periodEnd;

    private LocalDateTime updatedAt;
}
