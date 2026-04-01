package com.gamification.streaks.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XpTransactionDto {
    private String xpTransactionId;
    private String userId;
    private Integer xpAmount;
    private String xpSource;
    private String referenceId;
    private String description;
    private String createdAt;
}
