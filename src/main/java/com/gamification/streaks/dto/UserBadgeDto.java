package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserBadgeDto {
    private String userBadgeId;
    private String userId;
    private String badgeId;
    private String badgeName;
    private LocalDateTime createdData;
    private Integer xpAwarded;
}
