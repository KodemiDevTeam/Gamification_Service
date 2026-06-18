package com.gamification.streaks.dto;
import com.gamification.streaks.enums.ChallengeStatus;
import com.gamification.streaks.enums.ChallengeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ChallengeDto {
    private String challengeId;
    private String challengeName;
    private ChallengeType challengeType;
    private String description;
    private String targetType;
    private Integer targetCount;
    private Integer rewardXP;
    private String rewardBadge;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
    private ChallengeStatus status;
    private String userId;
    private String transactionId;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private List<RewardTierDto> rewardTiers;
    private Boolean notifyParticipants;   // new: Notify Participants toggle in Create Challenge form
}

