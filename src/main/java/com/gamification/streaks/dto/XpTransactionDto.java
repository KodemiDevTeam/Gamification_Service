package com.gamification.streaks.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XpTransactionDto {
    private String xpTransactionId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "XP amount is required")
    private Integer xpAmount;

    @NotBlank(message = "XP source is required")
    private String xpSource;

    @NotBlank(message = "Reference ID is required")
    private String referenceId;

    @NotBlank(message = "Description is required")
    private String description;

    private String createdAt;
}
