package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.RewardRuleController;
import com.gamification.streaks.dto.RewardRuleDto;
import com.gamification.streaks.model.RewardRule;
import com.gamification.streaks.service.RewardRuleService;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardRuleController.class)
class RewardRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardRuleService rewardRuleService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private RewardRule rewardRule;
    private RewardRuleDto rewardRuleDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        rewardRule = new RewardRule();
        rewardRule.setRuleId("rr-1");
        rewardRule.setRuleName("XP Reward");
        rewardRule.setRuleType("XP");
        rewardRule.setConditionType("STREAK_COUNT");
        rewardRule.setConditionValue(7);
        rewardRule.setRewardType("XP");
        rewardRule.setRewardValue(100);
        rewardRule.setActive(true);
        rewardRule.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));

        rewardRuleDto = new RewardRuleDto();
        rewardRuleDto.setRuleId("rr-1");
        rewardRuleDto.setRuleName("XP Reward");
        rewardRuleDto.setRuleType("XP");
        rewardRuleDto.setConditionType("STREAK_COUNT");
        rewardRuleDto.setConditionValue(7);
        rewardRuleDto.setRewardType("XP");
        rewardRuleDto.setRewardValue(100);
        rewardRuleDto.setActive(true);
        rewardRuleDto.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createRewardRuleFromDto_shouldReturn200() throws Exception {
        when(rewardRuleService.createRewardRule(any(RewardRuleDto.class))).thenReturn("Reward Rule Created Successfully");

        mockMvc.perform(post("/api/reward-rule/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardRuleDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Reward Rule Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createRewardRule_shouldReturn200() throws Exception {
        when(rewardRuleService.create(any(RewardRule.class))).thenReturn("Reward Rule Created Successfully");

        mockMvc.perform(post("/api/reward-rule/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardRule)))
                .andExpect(status().isOk())
                .andExpect(content().string("Reward Rule Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllRewardRules_shouldReturn200WithList() throws Exception {
        when(rewardRuleService.getAllRewardRule()).thenReturn(List.of(rewardRuleDto));

        mockMvc.perform(get("/api/reward-rule/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].ruleName").value("XP Reward"))
                .andExpect(jsonPath("$[0].conditionValue").value(7));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateRewardRule_shouldReturn200() throws Exception {
        when(rewardRuleService.updateRewardRule(eq("rr-1"), any(RewardRule.class)))
                .thenReturn("Reward Rule Updated Successfully");

        mockMvc.perform(put("/api/reward-rule/rr-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardRule)))
                .andExpect(status().isOk())
                .andExpect(content().string("Reward Rule Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteRewardRule_shouldReturn200() throws Exception {
        when(rewardRuleService.deleteRewardRule("rr-1")).thenReturn("Reward Rule Deleted Successfully");

        mockMvc.perform(delete("/api/reward-rule/rr-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Reward Rule Deleted Successfully"));
    }
}
