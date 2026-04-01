package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StreakRecordDto {
    private String streakId;
    private String userId;
    private String StreakType;
    private Integer currentStreakCount;
    private Integer longestStreakCount;
    private LocalDate lastActiveData;
    private String streakStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
