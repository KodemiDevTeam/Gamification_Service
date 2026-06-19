package com.gamification.streaks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.dto.*;
import com.gamification.streaks.enums.*;
import com.gamification.streaks.model.*;
import com.gamification.streaks.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BadgeRepository badgeRepository;

    @MockBean
    private ChallengeRepository challengeRepository;

    @MockBean
    private GamificationRuleRepository gamificationRuleRepository;

    @MockBean
    private LeaderBoardRepository leaderBoardRepository;

    @MockBean
    private RewardRuleRepository rewardRuleRepository;

    @MockBean
    private StreakRecordRepository streakRecordRepository;

    @MockBean
    private StreakRewardRepository streakRewardRepository;

    @MockBean
    private UserBadgeRepository userBadgeRepository;

    @MockBean
    private UserChallengeRepository userChallengeRepository;

    @MockBean
    private UserGamificationProfileRepository userGamificationProfileRepository;

    @MockBean
    private XpTransactionRepository xpTransactionRepository;

    @MockBean
    private ChallengeRuleRepository challengeRuleRepository;

    @MockBean
    private UserChallengePerformanceRepository userChallengePerformanceRepository;

    private String getTrainerToken() {
        return "Bearer " + Jwts.builder()
                .setSubject("trainer-user")
                .claim("role", "TRAINER")
                .signWith(Keys.hmacShaKeyFor("mysecretkeywhichmustbeatleast256bitslongsoitsecuresignsenough".getBytes()))
                .compact();
    }

    private String getLearnerToken() {
        return "Bearer " + Jwts.builder()
                .setSubject("learner-user")
                .claim("role", "LEARNER")
                .signWith(Keys.hmacShaKeyFor("mysecretkeywhichmustbeatleast256bitslongsoitsecuresignsenough".getBytes()))
                .compact();
    }

    @BeforeEach
    public void setUp() {
        // Badge mocks
        Badge sampleBadge = new Badge();
        sampleBadge.setBadgeId("test-badge-id");
        sampleBadge.setBadgeName("Test Badge");
        sampleBadge.setRewardType(RewardType.XP);
        when(badgeRepository.save(any(Badge.class))).thenReturn(sampleBadge);
        when(badgeRepository.findById(any(String.class))).thenReturn(sampleBadge);
        when(badgeRepository.findAll()).thenReturn(List.of(sampleBadge));

        // Challenge mocks
        Challenge sampleChallenge = new Challenge();
        sampleChallenge.setChallengeId("test-challenge-id");
        sampleChallenge.setChallengeName("Test Challenge");
        sampleChallenge.setActive(true);
        sampleChallenge.setStartDate(LocalDate.now().minusDays(1));
        sampleChallenge.setEndDate(LocalDate.now().plusDays(1));
        when(challengeRepository.save(any(Challenge.class))).thenReturn(sampleChallenge);
        when(challengeRepository.findById(any(String.class))).thenReturn(sampleChallenge);
        when(challengeRepository.findAll()).thenReturn(List.of(sampleChallenge));

        // GamificationRule mocks
        GamificationRule sampleRule = new GamificationRule();
        sampleRule.setRuleId("test-rule-id");
        sampleRule.setRuleName("Test Rule");
        sampleRule.setRuleType(RuleType.COIN_CONVERSION.name());
        sampleRule.setEnabled(true);
        when(gamificationRuleRepository.save(any(GamificationRule.class))).thenReturn(sampleRule);
        when(gamificationRuleRepository.findById(any(String.class))).thenReturn(sampleRule);
        when(gamificationRuleRepository.findAll()).thenReturn(List.of(sampleRule));

        // Leaderboard mocks
        LeaderBoardEntry sampleLeaderBoard = new LeaderBoardEntry();
        sampleLeaderBoard.setLeaderboardId("test-leaderboard-id");
        sampleLeaderBoard.setUserId("test-user");
        when(leaderBoardRepository.save(any(LeaderBoardEntry.class))).thenReturn(sampleLeaderBoard);
        when(leaderBoardRepository.findById(any(String.class))).thenReturn(sampleLeaderBoard);
        when(leaderBoardRepository.findAll()).thenReturn(List.of(sampleLeaderBoard));

        // RewardRule mocks
        RewardRule sampleRewardRule = new RewardRule();
        sampleRewardRule.setRuleId("test-reward-rule-id");
        sampleRewardRule.setRuleName("Test Reward Rule");
        sampleRewardRule.setActive(true);
        when(rewardRuleRepository.save(any(RewardRule.class))).thenReturn(sampleRewardRule);
        when(rewardRuleRepository.findById(any(String.class))).thenReturn(sampleRewardRule);
        when(rewardRuleRepository.findAll()).thenReturn(List.of(sampleRewardRule));

        // StreakRecord mocks
        StreakRecord sampleStreakRecord = new StreakRecord();
        sampleStreakRecord.setStreakId("test-streak-id");
        sampleStreakRecord.setUserId("test-user");
        when(streakRecordRepository.save(any(StreakRecord.class))).thenReturn(sampleStreakRecord);
        when(streakRecordRepository.findById(any(String.class))).thenReturn(sampleStreakRecord);
        when(streakRecordRepository.findAll()).thenReturn(List.of(sampleStreakRecord));
        when(streakRecordRepository.findByUserId(any(String.class))).thenReturn(List.of(sampleStreakRecord));

        // StreakReward mocks
        StreakReward sampleStreakReward = new StreakReward();
        sampleStreakReward.setStreakRewardId("test-streak-reward-id");
        sampleStreakReward.setStreakName("Test Streak Reward");
        when(streakRewardRepository.save(any(StreakReward.class))).thenReturn(sampleStreakReward);
        when(streakRewardRepository.findById(any(String.class))).thenReturn(sampleStreakReward);
        when(streakRewardRepository.findAll()).thenReturn(List.of(sampleStreakReward));

        // UserBadge mocks
        UserBadge sampleUserBadge = new UserBadge();
        sampleUserBadge.setUserBadgeId("test-user-badge-id");
        sampleUserBadge.setUserId("test-user");
        when(userBadgeRepository.save(any(UserBadge.class))).thenReturn(sampleUserBadge);
        when(userBadgeRepository.findById(any(String.class))).thenReturn(sampleUserBadge);
        when(userBadgeRepository.findAll()).thenReturn(List.of(sampleUserBadge));
        when(userBadgeRepository.findByUserId(any(String.class))).thenReturn(List.of(sampleUserBadge));

        // UserChallenge mocks
        UserChallenge sampleUserChallenge = new UserChallenge();
        sampleUserChallenge.setUserChallengeId("test-user-challenge-id");
        sampleUserChallenge.setUserId("test-user");
        sampleUserChallenge.setChallengeId("test-challenge-id");
        sampleUserChallenge.setStatus("IN_PROGRESS");
        when(userChallengeRepository.save(any(UserChallenge.class))).thenReturn(sampleUserChallenge);
        when(userChallengeRepository.findByUserIdAndChallengeId(any(String.class), any(String.class))).thenReturn(sampleUserChallenge);
        when(userChallengeRepository.findByUserId(any(String.class))).thenReturn(List.of(sampleUserChallenge));

        // UserGamificationProfile mocks
        UserGamificationProfile sampleProfile = new UserGamificationProfile();
        sampleProfile.setUserId("test-user");
        sampleProfile.setTotalXp(500);
        sampleProfile.setCoinBalance(10);
        sampleProfile.setLifetimeXp(500);
        when(userGamificationProfileRepository.save(any(UserGamificationProfile.class))).thenReturn(sampleProfile);
        when(userGamificationProfileRepository.findById(any(String.class))).thenReturn(sampleProfile);
        when(userGamificationProfileRepository.findAll()).thenReturn(List.of(sampleProfile));

        // XpTransaction mocks
        XpTransaction sampleXpTransaction = new XpTransaction();
        sampleXpTransaction.setXpTransactionId("test-xp-transaction-id");
        sampleXpTransaction.setUserId("test-user");
        when(xpTransactionRepository.save(any(XpTransaction.class))).thenReturn(sampleXpTransaction);
        when(xpTransactionRepository.findById(any(String.class))).thenReturn(sampleXpTransaction);
        when(xpTransactionRepository.findAll()).thenReturn(List.of(sampleXpTransaction));
        when(xpTransactionRepository.findByUserId(any(String.class))).thenReturn(List.of(sampleXpTransaction));

        // ChallengeRule mocks
        ChallengeRule sampleChallengeRule = new ChallengeRule();
        sampleChallengeRule.setRuleId("test-challenge-rule-id");
        sampleChallengeRule.setChallengeId("test-challenge-id");
        sampleChallengeRule.setTotalMarks(50);
        sampleChallengeRule.setPassMark(30);
        sampleChallengeRule.setRewardCoins(10);
        sampleChallengeRule.setRewardXp(20);
        sampleChallengeRule.setActive(true);
        when(challengeRuleRepository.save(any(ChallengeRule.class))).thenReturn(sampleChallengeRule);
        when(challengeRuleRepository.findById(any(String.class))).thenReturn(sampleChallengeRule);
        when(challengeRuleRepository.findAll()).thenReturn(List.of(sampleChallengeRule));
        when(challengeRuleRepository.findByChallengeId(any(String.class))).thenReturn(List.of(sampleChallengeRule));

        // UserChallengePerformance mocks
        UserChallengePerformance samplePerformance = new UserChallengePerformance();
        samplePerformance.setPerformanceId("test-performance-id");
        samplePerformance.setUserId("test-user");
        samplePerformance.setChallengeId("test-challenge-id");
        samplePerformance.setScore(40);
        samplePerformance.setPassed(true);
        samplePerformance.setCoinsEarned(10);
        samplePerformance.setXpEarned(20);
        when(userChallengePerformanceRepository.save(any(UserChallengePerformance.class))).thenReturn(samplePerformance);
        when(userChallengePerformanceRepository.findById(any(String.class))).thenReturn(samplePerformance);
        when(userChallengePerformanceRepository.findAll()).thenReturn(List.of(samplePerformance));
        when(userChallengePerformanceRepository.findByUserId(any(String.class))).thenReturn(List.of(samplePerformance));
        when(userChallengePerformanceRepository.findByChallengeId(any(String.class))).thenReturn(List.of(samplePerformance));
    }

    // ==========================================
    // Security / Authentication Tests
    // ==========================================

    @Test
    public void whenAnonymous_thenAccessBlocked() throws Exception {
        mockMvc.perform(get("/api/badge/all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenLearner_writesBlocked() throws Exception {
        BadgeDto badgeDto = new BadgeDto();
        badgeDto.setBadgeName("Forbidden Badge");

        mockMvc.perform(post("/api/badge/create-dto")
                        .header("Authorization", getLearnerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badgeDto)))
                .andExpect(status().isForbidden());
    }

    // ==========================================
    // 1. BadgeController Tests
    // ==========================================

    @Test
    public void testBadgeControllerEndpoints() throws Exception {
        BadgeDto badgeDto = new BadgeDto();
        badgeDto.setBadgeName("New Badge");
        badgeDto.setBadgeType("SPECIAL");

        Badge badge = new Badge();
        badge.setBadgeName("Generic Badge");

        // Create Dto
        mockMvc.perform(post("/api/badge/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badgeDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/badge/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Badge Created Successfully"));

        // Get by ID
        mockMvc.perform(get("/api/badge/test-badge-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/badge/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/badge/test-badge-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Badge Updated Successfully"));

        // Delete
        mockMvc.perform(delete("/api/badge/test-badge-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk())
                .andExpect(content().string("Badge Deleted Successfully"));
    }

    // ==========================================
    // 2. ChallengeController Tests
    // ==========================================

    @Test
    public void testChallengeControllerEndpoints() throws Exception {
        ChallengeDto challengeDto = new ChallengeDto();
        challengeDto.setChallengeName("New Challenge");

        Challenge challenge = new Challenge();
        challenge.setChallengeName("Generic Challenge");

        // Create DTO
        mockMvc.perform(post("/api/challenge/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(challengeDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/challenge/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(challenge)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/challenge/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/challenge/test-challenge-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(challenge)))
                .andExpect(status().isOk());

        // Hard Delete
        mockMvc.perform(delete("/api/challenge/test-challenge-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());

        // Archive (Soft Delete)
        mockMvc.perform(delete("/api/challenge/test-challenge-id/archive")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 3. GamificationRuleController Tests
    // ==========================================

    @Test
    public void testGamificationRuleControllerEndpoints() throws Exception {
        GamificationRuleDto ruleDto = new GamificationRuleDto();
        ruleDto.setRuleName("New Rule");

        GamificationRule rule = new GamificationRule();
        rule.setRuleName("Generic Rule");

        // Create DTO
        mockMvc.perform(post("/api/gamification-rule/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruleDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/gamification-rule/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/gamification-rule/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get Coin Conversion
        mockMvc.perform(get("/api/gamification-rule/coin-conversion")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/gamification-rule/test-rule-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk());

        // Toggle (PATCH) - any authenticated user has role for patch
        mockMvc.perform(patch("/api/gamification-rule/test-rule-id/toggle")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/gamification-rule/test-rule-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 4. LeaderBoardController Tests
    // ==========================================

    @Test
    public void testLeaderBoardControllerEndpoints() throws Exception {
        LeaderBoardEntryDto entryDto = new LeaderBoardEntryDto();
        entryDto.setUserId("test-user");

        LeaderBoardEntry entry = new LeaderBoardEntry();
        entry.setUserId("test-user");

        // Create DTO
        mockMvc.perform(post("/api/leaderboard/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entryDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/leaderboard/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/leaderboard/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/leaderboard/test-leaderboard-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/leaderboard/test-leaderboard-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 5. RewardRuleController Tests
    // ==========================================

    @Test
    public void testRewardRuleControllerEndpoints() throws Exception {
        RewardRuleDto ruleDto = new RewardRuleDto();
        ruleDto.setRuleName("Reward Rule DTO");

        RewardRule rule = new RewardRule();
        rule.setRuleName("Generic Reward Rule");

        // Create DTO
        mockMvc.perform(post("/api/reward-rule/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruleDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/reward-rule/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/reward-rule/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/reward-rule/test-reward-rule-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk());

        // Toggle (PATCH)
        mockMvc.perform(patch("/api/reward-rule/test-reward-rule-id/toggle")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/reward-rule/test-reward-rule-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 6. StreakRecordController Tests
    // ==========================================

    @Test
    public void testStreakRecordControllerEndpoints() throws Exception {
        StreakRecordDto recordDto = new StreakRecordDto();
        recordDto.setUserId("test-user");

        StreakRecord record = new StreakRecord();
        record.setUserId("test-user");

        // Create DTO
        mockMvc.perform(post("/api/streak-record/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/streak-record/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/streak-record/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get User Streak Records
        mockMvc.perform(get("/api/streak-record/user/test-user")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/streak-record/test-streak-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/streak-record/test-streak-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 7. StreakRewardController Tests
    // ==========================================

    @Test
    public void testStreakRewardControllerEndpoints() throws Exception {
        StreakRewardDto rewardDto = new StreakRewardDto();
        rewardDto.setStreakName("Streak Reward DTO");

        StreakReward reward = new StreakReward();
        reward.setStreakName("Generic Streak Reward");

        // Create DTO
        mockMvc.perform(post("/api/streak-rewards/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/streak-rewards/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reward)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/streak-rewards/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get By ID
        mockMvc.perform(get("/api/streak-rewards/test-streak-reward-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/streak-rewards/test-streak-reward-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reward)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/streak-rewards/test-streak-reward-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 8. UserBadgeController Tests
    // ==========================================

    @Test
    public void testUserBadgeControllerEndpoints() throws Exception {
        UserBadgeDto userBadgeDto = new UserBadgeDto();
        userBadgeDto.setUserId("test-user");
        userBadgeDto.setBadgeId("test-badge-id");

        UserBadge userBadge = new UserBadge();
        userBadge.setUserId("test-user");
        userBadge.setBadgeId("test-badge-id");

        // Create DTO
        mockMvc.perform(post("/api/user-badge/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBadgeDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/user-badge/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBadge)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/user-badge/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get User Badges by UserID
        mockMvc.perform(get("/api/user-badge/user/test-user")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/user-badge/test-user-badge-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBadge)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/user-badge/test-user-badge-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 9. UserChallengeController Tests
    // ==========================================

    @Test
    public void testUserChallengeControllerEndpoints() throws Exception {
        // Enroll User
        mockMvc.perform(post("/api/user-challenge/enroll")
                        .header("Authorization", getTrainerToken())
                        .param("userId", "test-user")
                        .param("challengeId", "test-challenge-id"))
                .andExpect(status().isOk());

        // Progress User
        mockMvc.perform(post("/api/user-challenge/progress")
                        .header("Authorization", getTrainerToken())
                        .param("userId", "test-user")
                        .param("challengeId", "test-challenge-id")
                        .param("progress", "5"))
                .andExpect(status().isOk());

        // Get User Challenges by UserId
        mockMvc.perform(get("/api/user-challenge/test-user")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get Specific User Challenge
        mockMvc.perform(get("/api/user-challenge/test-user/test-challenge-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 10. UserGamificationProfileController Tests
    // ==========================================

    @Test
    public void testUserGamificationProfileControllerEndpoints() throws Exception {
        UserGamificationProfileDto profileDto = new UserGamificationProfileDto();
        profileDto.setRole(UserRole.LEARNER);

        UserGamificationProfile profile = new UserGamificationProfile();
        profile.setRole(UserRole.LEARNER);

        // Create DTO
        mockMvc.perform(post("/api/user-gamification/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/user-gamification/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());

        // Get Profile By UserID
        mockMvc.perform(get("/api/user-gamification/test-user")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/user-gamification/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Checkout Calculate
        mockMvc.perform(get("/api/user-gamification/test-user/checkout-calculate")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Convert XP
        mockMvc.perform(post("/api/user-gamification/test-user/convert-xp")
                        .header("Authorization", getTrainerToken())
                        .param("coins", "2"))
                .andExpect(status().isOk());

        // Redeem Coins
        mockMvc.perform(post("/api/user-gamification/test-user/redeem")
                        .header("Authorization", getTrainerToken())
                        .param("coins", "5"))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/user-gamification/test-user")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/user-gamification/test-user")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 11. XpTransactionController Tests
    // ==========================================

    @Test
    public void testXpTransactionControllerEndpoints() throws Exception {
        XpTransactionDto transactionDto = new XpTransactionDto();
        transactionDto.setUserId("test-user");
        transactionDto.setXpAmount(50);
        transactionDto.setXpSource(XpSource.CHALLENGE_COMPLETION);

        XpTransaction transaction = new XpTransaction();
        transaction.setUserId("test-user");
        transaction.setXpAmount(50);
        transaction.setXpSource(XpSource.CHALLENGE_COMPLETION);

        // Create DTO
        mockMvc.perform(post("/api/xp-transaction/create-dto")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk());

        // Create Entity
        mockMvc.perform(post("/api/xp-transaction/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/xp-transaction/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get By UserID
        mockMvc.perform(get("/api/xp-transaction/user/test-user")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/xp-transaction/test-xp-transaction-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/xp-transaction/test-xp-transaction-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 12. ChallengeRuleController Tests
    // ==========================================

    @Test
    public void testChallengeRuleControllerEndpoints() throws Exception {
        ChallengeRuleDto dto = new ChallengeRuleDto();
        dto.setChallengeId("test-challenge-id");
        dto.setTotalMarks(50);
        dto.setPassMark(30);
        dto.setRewardCoins(10);
        dto.setRewardXp(20);

        ChallengeRule rule = new ChallengeRule();
        rule.setChallengeId("test-challenge-id");

        // Create
        mockMvc.perform(post("/api/challenge-rules/create")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Get by ID
        mockMvc.perform(get("/api/challenge-rules/test-challenge-rule-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get by Challenge ID
        mockMvc.perform(get("/api/challenge-rules/challenge/test-challenge-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/challenge-rules/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Update
        mockMvc.perform(put("/api/challenge-rules/test-challenge-rule-id")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/challenge-rules/test-challenge-rule-id")
                        .header("Authorization", getTrainerToken()))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 13. UserChallengePerformanceController Tests
    // ==========================================

    @Test
    public void testUserChallengePerformanceControllerEndpoints() throws Exception {
        UserChallengePerformanceDto dto = new UserChallengePerformanceDto();
        dto.setUserId("test-user");
        dto.setChallengeId("test-challenge-id");
        dto.setScore(40);

        // Submit
        mockMvc.perform(post("/api/user-performance/submit")
                        .header("Authorization", getTrainerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Get by ID
        mockMvc.perform(get("/api/user-performance/test-performance-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get by User ID
        mockMvc.perform(get("/api/user-performance/user/test-user")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get by Challenge ID
        mockMvc.perform(get("/api/user-performance/challenge/test-challenge-id")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());

        // Get All
        mockMvc.perform(get("/api/user-performance/all")
                        .header("Authorization", getLearnerToken()))
                .andExpect(status().isOk());
    }
}
