package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserChallengeDto {
    private String userChallengeId;
    private String userId;
    private String challengeId;
    private String status;
    private Integer progress;
    private LocalDateTime joinedAt;
    private LocalDateTime completedAt;
}
