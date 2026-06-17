package com.gamification.streaks.service;

import com.gamification.streaks.dto.UserChallengeDto;
import java.util.List;

public interface UserChallengeService {
    UserChallengeDto enrollUser(String userId, String challengeId);
    UserChallengeDto updateProgress(String userId, String challengeId, Integer progress);
    UserChallengeDto getUserChallenge(String userId, String challengeId);
    List<UserChallengeDto> getUserChallenges(String userId);
}
