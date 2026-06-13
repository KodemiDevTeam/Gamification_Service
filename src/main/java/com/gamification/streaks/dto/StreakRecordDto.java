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
public class StreakRecordDto {
    private String streakId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Streak type is required")
    private String StreakType;

    @NotNull(message = "Current streak count is required")
    @Min(value = 0, message = "Current streak count cannot be negative")
    private Integer currentStreakCount;

    @NotNull(message = "Longest streak count is required")
    @Min(value = 0, message = "Longest streak count cannot be negative")
    private Integer longestStreakCount;

    @NotNull(message = "Last active date is required")
    private LocalDate lastActiveData;

    @NotBlank(message = "Streak status is required")
    private String streakStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
