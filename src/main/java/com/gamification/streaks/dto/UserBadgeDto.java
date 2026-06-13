package com.gamification.streaks.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserBadgeDto {
    private String userBadgeId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Badge ID is required")
    private String badgeId;

    @NotBlank(message = "Badge name is required")
    private String badgeName;

    private LocalDateTime createdData;

    @NotNull(message = "XP awarded is required")
    @Min(value = 0, message = "XP awarded cannot be negative")
    private Integer xpAwarded;
}
