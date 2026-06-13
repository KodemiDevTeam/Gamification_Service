package com.gamification.streaks.service;

import com.gamification.streaks.dto.ChallengeDto;
import com.gamification.streaks.model.Challenge;

import java.util.List;

public interface ChallengeService {
    String createChallenge(ChallengeDto challengeDto);
    String create(Challenge challenge);
    List<ChallengeDto> getAllChallenge();
    String updateChallenge(String challengeId,Challenge challenge);
    String deleteChallenge(String challengeId);
}
