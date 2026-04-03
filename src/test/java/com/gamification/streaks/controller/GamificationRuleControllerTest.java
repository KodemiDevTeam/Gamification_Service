package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.GamificationRuleController;
import com.gamification.streaks.dto.GamificationRuleDto;
import com.gamification.streaks.model.GamificationRule;
import com.gamification.streaks.service.GamificationRuleService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GamificationRuleController.class)
class GamificationRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GamificationRuleService gamificationRuleService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private GamificationRule rule;
    private GamificationRuleDto ruleDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        rule = new GamificationRule();
        rule.setRuleId("rule-1");
        rule.setRuleName("XP Rule");
        rule.setRuleType("XP");
        rule.setConfig("{\"multiplier\": 2}");
        rule.setEnabled(true);
        rule.setVersion(1);
        rule.setAbCohort("A");
        rule.setCreatedAt("2026-01-01");
        rule.setUpdatedAt("2026-01-01");
        rule.setUpdatedBy("admin");

        ruleDto = new GamificationRuleDto();
        ruleDto.setRuleId("rule-1");
        ruleDto.setRuleName("XP Rule");
        ruleDto.setRuleType("XP");
        ruleDto.setConfig("{\"multiplier\": 2}");
        ruleDto.setEnabled(true);
        ruleDto.setVersion(1);
        ruleDto.setAbCohort("A");
        ruleDto.setCreatedAt("2026-01-01");
        ruleDto.setUpdatedAt("2026-01-01");
        ruleDto.setUpdatedBy("admin");
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createGamificationRuleFromDto_shouldReturn200() throws Exception {
        when(gamificationRuleService.createGamificationRule(any(GamificationRuleDto.class)))
                .thenReturn("Gamification Rule Created Successfully");

        mockMvc.perform(post("/api/gamification-rule/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruleDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Gamification Rule Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createGamificationRule_shouldReturn200() throws Exception {
        when(gamificationRuleService.create(any(GamificationRule.class)))
                .thenReturn("Gamification Rule Created Successfully");

        mockMvc.perform(post("/api/gamification-rule/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk())
                .andExpect(content().string("Gamification Rule Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateGamificationRule_shouldReturn200WithList() throws Exception {
        when(gamificationRuleService.update(eq("rule-1"), any(GamificationRule.class)))
                .thenReturn(List.of(ruleDto));

        mockMvc.perform(put("/api/gamification-rule/rule-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].ruleName").value("XP Rule"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteGamificationRule_shouldReturn200() throws Exception {
        when(gamificationRuleService.deleteGamificationRule("rule-1"))
                .thenReturn("Gamification Rule Deleted Successfully");

        mockMvc.perform(delete("/api/gamification-rule/rule-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Gamification Rule Deleted Successfully"));
    }
}
