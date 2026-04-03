package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.BadgeController;
import com.gamification.streaks.dto.BadgeDto;
import com.gamification.streaks.model.Badge;
import com.gamification.streaks.service.BadgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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

@WebMvcTest(BadgeController.class)
class BadgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BadgeService badgeService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private Badge badge;
    private BadgeDto badgeDto;

    @BeforeEach
    void setUp() throws Exception {
        // Make the mocked JwtFilter a pass-through so @WithMockUser security context is preserved
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(
                    inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        badge = new Badge();
        badge.setBadgeId("badge-1");
        badge.setBadgeName("Gold Badge");
        badge.setBadgeType("STREAK");
        badge.setDescription("7-day streak badge");
        badge.setIconUrl("http://icon.url/gold.png");
        badge.setXpReward(100);
        badge.setEligibilityRule("streak >= 7");
        badge.setActive(true);
        badge.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));

        badgeDto = new BadgeDto();
        badgeDto.setBadgeId("badge-1");
        badgeDto.setBadgeName("Gold Badge");
        badgeDto.setBadgeType("STREAK");
        badgeDto.setDescription("7-day streak badge");
        badgeDto.setIconUrl("http://icon.url/gold.png");
        badgeDto.setXpReward(100);
        badgeDto.setEligibilityRule("streak >= 7");
        badgeDto.setActive(true);
        badgeDto.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createBadgeFromDto_shouldReturn200WithBadge() throws Exception {
        when(badgeService.createBadge(any(BadgeDto.class))).thenReturn(badge);

        mockMvc.perform(post("/api/badge/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badgeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.badgeId").value("badge-1"))
                .andExpect(jsonPath("$.badgeName").value("Gold Badge"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createBadge_shouldReturn200WithSuccessMessage() throws Exception {
        when(badgeService.createBade(any(Badge.class))).thenReturn("Badge Created Successfully");

        mockMvc.perform(post("/api/badge/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Badge Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getBadgeById_shouldReturn200WithDto() throws Exception {
        when(badgeService.getBadgeId("badge-1")).thenReturn(badgeDto);

        mockMvc.perform(get("/api/badge/badge-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.badgeId").value("badge-1"))
                .andExpect(jsonPath("$.xpReward").value(100));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllBadges_shouldReturn200WithList() throws Exception {
        when(badgeService.getAllBadge()).thenReturn(List.of(badgeDto));

        mockMvc.perform(get("/api/badge/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].badgeName").value("Gold Badge"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateBadge_shouldReturn200WithSuccessMessage() throws Exception {
        when(badgeService.updateBadge(eq("badge-1"), any(Badge.class))).thenReturn("Badge Updated Successfully");

        mockMvc.perform(put("/api/badge/badge-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Badge Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteBadge_shouldReturn200WithSuccessMessage() throws Exception {
        when(badgeService.deleteBadge("badge-1")).thenReturn("Badge Deleted Successfully");

        mockMvc.perform(delete("/api/badge/badge-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Badge Deleted Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void createBadge_asLearner_shouldReturn403() throws Exception {
        // LEARNER role cannot POST — no csrf() so CSRF check triggers 403
        mockMvc.perform(post("/api/badge/create-dto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badgeDto)))
                .andExpect(status().isForbidden());
    }
}
