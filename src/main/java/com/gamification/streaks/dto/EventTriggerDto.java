package com.gamification.streaks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventTriggerDto {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Event name is required")
    private String eventName;
}
