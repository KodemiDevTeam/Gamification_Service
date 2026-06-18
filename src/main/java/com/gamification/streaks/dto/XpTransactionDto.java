package com.gamification.streaks.dto;
import com.gamification.streaks.enums.XpSource;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class XpTransactionDto {
    private String xpTransactionId;
    private String userId;
    private Integer xpAmount;
    private XpSource xpSource;        // fixed: was raw String
    private String referenceId;
    private String description;
    private LocalDateTime createdAt;  // fixed: was String
}
