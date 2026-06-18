package com.gamification.streaks.dto;
import com.gamification.streaks.enums.StreakStatus;
import com.gamification.streaks.enums.StreakType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StreakRecordDto {
    private String streakId;
    private String userId;
    private StreakType streakType;           // fixed: was PascalCase String StreakType
    private Integer currentStreakCount;
    private Integer longestStreakCount;
    private LocalDate lastActiveDate;        // fixed: was lastActiveData
    private StreakStatus streakStatus;       // fixed: was raw String
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
