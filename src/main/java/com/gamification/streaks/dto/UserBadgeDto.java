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
    private String badgeIconUrl;
    private Integer xpAwarded;
    private String awardedBy;
    private LocalDateTime createdAt;     // fixed: was createdData
    private LocalDateTime revokedAt;
    private String revokeReason;
}
