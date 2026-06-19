package com.gamification.streaks.service;

import com.gamification.streaks.dto.UserChallengePerformanceDto;
import com.gamification.streaks.model.UserChallengePerformance;

import java.util.List;

public interface UserChallengePerformanceService {
    UserChallengePerformance submitPerformance(UserChallengePerformanceDto dto);
    UserChallengePerformance getPerformanceById(String performanceId);
    List<UserChallengePerformance> getPerformancesByUserId(String userId);
    List<UserChallengePerformance> getPerformancesByChallengeId(String challengeId);
    List<UserChallengePerformance> getAllPerformances();
}
