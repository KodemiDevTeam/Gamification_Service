package com.gamification.streaks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevokeBadgeRequest {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Badge ID is required")
    private String badgeId;

    @NotBlank(message = "Reason is required")
    private String reason;
}
