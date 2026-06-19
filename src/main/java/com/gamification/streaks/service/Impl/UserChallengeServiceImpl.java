package com.gamification.streaks.service.Impl;

import com.gamification.streaks.dto.UserChallengeDto;
import com.gamification.streaks.enums.XpSource;
import com.gamification.streaks.model.*;
import com.gamification.streaks.repository.*;
import com.gamification.streaks.service.UserChallengeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserChallengeServiceImpl implements UserChallengeService {

    private final UserChallengeRepository userChallengeRepository;
    private final ChallengeRepository challengeRepository;
    private final UserGamificationProfileRepository userGamificationProfileRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final XpTransactionRepository xpTransactionRepository;

    public UserChallengeServiceImpl(
            UserChallengeRepository userChallengeRepository,
            ChallengeRepository challengeRepository,
            UserGamificationProfileRepository userGamificationProfileRepository,
            UserBadgeRepository userBadgeRepository,
            XpTransactionRepository xpTransactionRepository) {
        this.userChallengeRepository = userChallengeRepository;
        this.challengeRepository = challengeRepository;
        this.userGamificationProfileRepository = userGamificationProfileRepository;
        this.userBadgeRepository = userBadgeRepository;
        this.xpTransactionRepository = xpTransactionRepository;
    }

    @Override
    public UserChallengeDto enrollUser(String userId, String challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId);
        if (challenge == null) {
            throw new RuntimeException("Challenge not found");
        }

        UserChallenge existing = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (existing != null) {
            return mapToDto(existing);
        }

        if (challenge.getMaxParticipants() != null && challenge.getCurrentParticipants() != null) {
            if (challenge.getCurrentParticipants() >= challenge.getMaxParticipants()) {
                throw new RuntimeException("Challenge is already full");
            }
        }

        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setUserChallengeId(UUID.randomUUID().toString());
        userChallenge.setUserId(userId);
        userChallenge.setChallengeId(challengeId);
        userChallenge.setStatus("IN_PROGRESS");
        userChallenge.setProgress(0);
        userChallenge.setJoinedAt(LocalDateTime.now());

        // Increment participant count
        int current = challenge.getCurrentParticipants() != null ? challenge.getCurrentParticipants() : 0;
        challenge.setCurrentParticipants(current + 1);
        challengeRepository.save(challenge);

        userChallengeRepository.save(userChallenge);
        return mapToDto(userChallenge);
    }

    @Override
    public UserChallengeDto updateProgress(String userId, String challengeId, Integer progress) {
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (userChallenge == null) {
            throw new RuntimeException("User enrollment for this challenge not found");
        }

        if (!"IN_PROGRESS".equals(userChallenge.getStatus())) {
            return mapToDto(userChallenge);
        }

        Challenge challenge = challengeRepository.findById(challengeId);
        if (challenge == null) {
            throw new RuntimeException("Challenge not found");
        }

        userChallenge.setProgress(progress);

        int target = challenge.getTargetCount() != null ? challenge.getTargetCount() : 0;
        if (progress >= target) {
            userChallenge.setStatus("COMPLETED");
            userChallenge.setCompletedAt(LocalDateTime.now());

            // Award rewards
            UserGamificationProfile profile = userGamificationProfileRepository.findById(userId);
            if (profile != null) {
                int rewardXp = challenge.getRewardXP() != null ? challenge.getRewardXP() : 0;
                if (rewardXp > 0) {
                    profile.setTotalXp((profile.getTotalXp() != null ? profile.getTotalXp() : 0) + rewardXp);
                    profile.setLifetimeXp((profile.getLifetimeXp() != null ? profile.getLifetimeXp() : 0) + rewardXp);
                    profile.setUpdatedAt(LocalDateTime.now());
                    userGamificationProfileRepository.save(profile);

                    // Audit XP Transaction
                    XpTransaction transaction = new XpTransaction();
                    transaction.setXpTransactionId(UUID.randomUUID().toString());
                    transaction.setUserId(userId);
                    transaction.setXpAmount(rewardXp);
                    transaction.setXpSource(XpSource.CHALLENGE_COMPLETION);
                    transaction.setReferenceId(challengeId);
                    transaction.setDescription("Completed challenge: " + challenge.getChallengeName());
                    transaction.setCreatedAt(LocalDateTime.now());
                    xpTransactionRepository.save(transaction);
                }

                // Award Badge
                if (challenge.getRewardBadge() != null && !challenge.getRewardBadge().trim().isEmpty()) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserBadgeId(UUID.randomUUID().toString());
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(UUID.randomUUID().toString()); // Mock badgeId mapping
                    userBadge.setBadgeName(challenge.getRewardBadge());
                    userBadge.setCreatedAt(LocalDateTime.now());
                    userBadge.setXpAwarded(rewardXp);
                    userBadgeRepository.save(userBadge);

                    profile.setTotalBadges((profile.getTotalBadges() != null ? profile.getTotalBadges() : 0) + 1);
                    userGamificationProfileRepository.save(profile);
                }
            }
        }

        userChallengeRepository.save(userChallenge);
        return mapToDto(userChallenge);
    }

    @Override
    public UserChallengeDto getUserChallenge(String userId, String challengeId) {
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (userChallenge == null) {
            return null;
        }
        return mapToDto(userChallenge);
    }

    @Override
    public List<UserChallengeDto> getUserChallenges(String userId) {
        List<UserChallenge> list = userChallengeRepository.findByUserId(userId);
        List<UserChallengeDto> dtos = new ArrayList<>();
        for (UserChallenge uc : list) {
            dtos.add(mapToDto(uc));
        }
        return dtos;
    }

    private UserChallengeDto mapToDto(UserChallenge userChallenge) {
        if (userChallenge == null) return null;
        UserChallengeDto dto = new UserChallengeDto();
        dto.setUserChallengeId(userChallenge.getUserChallengeId());
        dto.setUserId(userChallenge.getUserId());
        dto.setChallengeId(userChallenge.getChallengeId());
        dto.setStatus(userChallenge.getStatus());
        dto.setProgress(userChallenge.getProgress());
        dto.setJoinedAt(userChallenge.getJoinedAt());
        dto.setCompletedAt(userChallenge.getCompletedAt());
        return dto;
    }
}
