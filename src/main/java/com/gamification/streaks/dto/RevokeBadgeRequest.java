package com.gamification.streaks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevokeBadgeRequest {
    private String userId;
    private String badgeId;
    private String reason;
}
