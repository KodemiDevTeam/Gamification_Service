package com.gamification.streaks.service.Impl;

import com.gamification.streaks.dto.UserChallengePerformanceDto;
import com.gamification.streaks.enums.XpSource;
import com.gamification.streaks.model.*;
import com.gamification.streaks.repository.*;
import com.gamification.streaks.service.UserChallengePerformanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserChallengePerformanceServiceImpl implements UserChallengePerformanceService {

    private final UserChallengePerformanceRepository performanceRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeRuleRepository challengeRuleRepository;
    private final UserGamificationProfileRepository profileRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final XpTransactionRepository xpTransactionRepository;

    public UserChallengePerformanceServiceImpl(
            UserChallengePerformanceRepository performanceRepository,
            ChallengeRepository challengeRepository,
            ChallengeRuleRepository challengeRuleRepository,
            UserGamificationProfileRepository profileRepository,
            UserBadgeRepository userBadgeRepository,
            XpTransactionRepository xpTransactionRepository) {
        this.performanceRepository = performanceRepository;
        this.challengeRepository = challengeRepository;
        this.challengeRuleRepository = challengeRuleRepository;
        this.profileRepository = profileRepository;
        this.userBadgeRepository = userBadgeRepository;
        this.xpTransactionRepository = xpTransactionRepository;
    }

    @Override
    public UserChallengePerformance submitPerformance(UserChallengePerformanceDto dto) {
        Challenge challenge = challengeRepository.findById(dto.getChallengeId());
        if (challenge == null) {
            throw new RuntimeException("Challenge not found");
        }

        UserGamificationProfile profile = profileRepository.findById(dto.getUserId());
        if (profile == null) {
            throw new RuntimeException("User Gamification Profile not found");
        }

        // Find active rules for this challenge
        List<ChallengeRule> rules = challengeRuleRepository.findByChallengeId(dto.getChallengeId());
        ChallengeRule activeRule = rules.stream()
                .filter(r -> Boolean.TRUE.equals(r.getActive()))
                .findFirst()
                .orElse(null);

        boolean passed = false;
        int coinsEarned = 0;
        int xpEarned = 0;

        if (activeRule != null) {
            int score = dto.getScore() != null ? dto.getScore() : 0;
            int passMark = activeRule.getPassMark() != null ? activeRule.getPassMark() : 0;
            if (score >= passMark) {
                passed = true;
                coinsEarned = activeRule.getRewardCoins() != null ? activeRule.getRewardCoins() : 0;
                xpEarned = activeRule.getRewardXp() != null ? activeRule.getRewardXp() : 0;
            }
        } else {
            // Fallback to challenge default if no custom active rule is set
            int target = challenge.getTargetCount() != null ? challenge.getTargetCount() : 0;
            int progress = dto.getScore() != null ? dto.getScore() : 0;
            if (progress >= target) {
                passed = true;
                xpEarned = challenge.getRewardXP() != null ? challenge.getRewardXP() : 0;
            }
        }

        // Save performance record
        UserChallengePerformance performance = new UserChallengePerformance();
        performance.setPerformanceId(UUID.randomUUID().toString());
        performance.setUserId(dto.getUserId());
        performance.setChallengeId(dto.getChallengeId());
        performance.setScore(dto.getScore());
        performance.setPassed(passed);
        performance.setCoinsEarned(coinsEarned);
        performance.setXpEarned(xpEarned);
        performance.setPerformedAt(LocalDateTime.now().toString());
        performanceRepository.save(performance);

        if (passed) {
            // 1. Update user profile XP and coins
            int profileCoins = profile.getCoinBalance() != null ? profile.getCoinBalance() : 0;
            profile.setCoinBalance(profileCoins + coinsEarned);

            if (xpEarned > 0) {
                int profileXp = profile.getTotalXp() != null ? profile.getTotalXp() : 0;
                int profileLifetimeXp = profile.getLifetimeXp() != null ? profile.getLifetimeXp() : 0;
                profile.setTotalXp(profileXp + xpEarned);
                profile.setLifetimeXp(profileLifetimeXp + xpEarned);

                // Audit XP Transaction
                XpTransaction transaction = new XpTransaction();
                transaction.setXpTransactionId(UUID.randomUUID().toString());
                transaction.setUserId(dto.getUserId());
                transaction.setXpAmount(xpEarned);
                transaction.setXpSource(XpSource.CHALLENGE_COMPLETION);
                transaction.setReferenceId(dto.getChallengeId());
                transaction.setDescription("Passed challenge: " + challenge.getChallengeName());
                transaction.setCreatedAt(LocalDateTime.now());
                xpTransactionRepository.save(transaction);
            }

            profile.setUpdatedAt(LocalDateTime.now());
            profileRepository.save(profile);

            // 2. Award Challenge Specific Badge
            if (challenge.getRewardBadge() != null && !challenge.getRewardBadge().trim().isEmpty()) {
                List<UserBadge> existingBadges = userBadgeRepository.findByUserId(dto.getUserId());
                boolean hasBadge = existingBadges.stream()
                        .anyMatch(b -> challenge.getRewardBadge().equalsIgnoreCase(b.getBadgeName()));
                if (!hasBadge) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserBadgeId(UUID.randomUUID().toString());
                    userBadge.setUserId(dto.getUserId());
                    userBadge.setBadgeId(UUID.randomUUID().toString());
                    userBadge.setBadgeName(challenge.getRewardBadge());
                    userBadge.setCreatedAt(LocalDateTime.now());
                    userBadge.setXpAwarded(xpEarned);
                    userBadgeRepository.save(userBadge);

                    profile.setTotalBadges((profile.getTotalBadges() != null ? profile.getTotalBadges() : 0) + 1);
                    profileRepository.save(profile);
                }
            }

            // 3. Award Milestone XP Badge
            int currentLifetimeXp = profile.getLifetimeXp() != null ? profile.getLifetimeXp() : 0;
            if (currentLifetimeXp >= 1000) {
                List<UserBadge> existingBadges = userBadgeRepository.findByUserId(dto.getUserId());
                boolean hasMilestoneBadge = existingBadges.stream()
                        .anyMatch(b -> "XP Milestone Champion".equalsIgnoreCase(b.getBadgeName()));
                if (!hasMilestoneBadge) {
                    UserBadge milestoneBadge = new UserBadge();
                    milestoneBadge.setUserBadgeId(UUID.randomUUID().toString());
                    milestoneBadge.setUserId(dto.getUserId());
                    milestoneBadge.setBadgeId(UUID.randomUUID().toString());
                    milestoneBadge.setBadgeName("XP Milestone Champion");
                    milestoneBadge.setCreatedAt(LocalDateTime.now());
                    milestoneBadge.setXpAwarded(0);
                    userBadgeRepository.save(milestoneBadge);

                    profile.setTotalBadges((profile.getTotalBadges() != null ? profile.getTotalBadges() : 0) + 1);
                    profileRepository.save(profile);
                }
            }
        }

        return performance;
    }

    @Override
    public UserChallengePerformance getPerformanceById(String performanceId) {
        UserChallengePerformance performance = performanceRepository.findById(performanceId);
        if (performance == null) {
            throw new RuntimeException("Performance record not found");
        }
        return performance;
    }

    @Override
    public List<UserChallengePerformance> getPerformancesByUserId(String userId) {
        return performanceRepository.findByUserId(userId);
    }

    @Override
    public List<UserChallengePerformance> getPerformancesByChallengeId(String challengeId) {
        return performanceRepository.findByChallengeId(challengeId);
    }

    @Override
    public List<UserChallengePerformance> getAllPerformances() {
        return performanceRepository.findAll();
    }
}
