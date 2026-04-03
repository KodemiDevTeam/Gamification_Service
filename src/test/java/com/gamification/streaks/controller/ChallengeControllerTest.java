package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.ChallengeController;
import com.gamification.streaks.dto.ChallengeDto;
import com.gamification.streaks.model.Challenge;
import com.gamification.streaks.service.ChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChallengeService challengeService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private Challenge challenge;
    private ChallengeDto challengeDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        challenge = new Challenge();
        challenge.setChallengeId("ch-1");
        challenge.setChallengeName("7-Day Streak");
        challenge.setChallengeType("STREAK");
        challenge.setDescription("Complete 7 days in a row");
        challenge.setRewardXP(500);
        challenge.setRewardBadge("streak-badge");
        challenge.setStartDate(LocalDate.of(2026, 1, 1));
        challenge.setEndDate(LocalDate.of(2026, 1, 31));
        challenge.setActive(true);

        challengeDto = new ChallengeDto();
        challengeDto.setChallengeId("ch-1");
        challengeDto.setChallengeName("7-Day Streak");
        challengeDto.setChallengeType("STREAK");
        challengeDto.setDescription("Complete 7 days in a row");
        challengeDto.setRewardXP(500);
        challengeDto.setRewardBadge("streak-badge");
        challengeDto.setStartDate(LocalDate.of(2026, 1, 1));
        challengeDto.setEndDate(LocalDate.of(2026, 1, 31));
        challengeDto.setActive(true);
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createChallengeFromDto_shouldReturn200() throws Exception {
        when(challengeService.createChallenge(any(ChallengeDto.class))).thenReturn("Challenge Created Successfully");

        mockMvc.perform(post("/api/challenge/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(challengeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Challenge Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createChallenge_shouldReturn200() throws Exception {
        when(challengeService.create(any(Challenge.class))).thenReturn("Challenge Created Successfully");

        mockMvc.perform(post("/api/challenge/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(challenge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Challenge Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllChallenges_shouldReturn200WithList() throws Exception {
        when(challengeService.getAllChallenge()).thenReturn(List.of(challengeDto));

        mockMvc.perform(get("/api/challenge/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].challengeName").value("7-Day Streak"))
                .andExpect(jsonPath("$[0].rewardXP").value(500));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateChallenge_shouldReturn200() throws Exception {
        when(challengeService.updateChallenge(eq("ch-1"), any(Challenge.class)))
                .thenReturn("Challenge Updated Successfully");

        mockMvc.perform(put("/api/challenge/ch-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(challenge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Challenge Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteChallenge_shouldReturn200() throws Exception {
        when(challengeService.deleteChallenge("ch-1")).thenReturn("Challenge Deleted Successfully");

        mockMvc.perform(delete("/api/challenge/ch-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Challenge Deleted Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void deleteChallenge_asLearner_shouldReturn403() throws Exception {
        // LEARNER cannot DELETE — no csrf() so CSRF check triggers 403
        mockMvc.perform(delete("/api/challenge/ch-1"))
                .andExpect(status().isForbidden());
    }
}
