package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.UserBadgeController;
import com.gamification.streaks.dto.UserBadgeDto;
import com.gamification.streaks.model.UserBadge;
import com.gamification.streaks.service.UserBadgeService;
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

@WebMvcTest(UserBadgeController.class)
class UserBadgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBadgeService userBadgeService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private UserBadge userBadge;
    private UserBadgeDto userBadgeDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        userBadge = new UserBadge();
        userBadge.setUserBadgeId("ub-1");
        userBadge.setUserId("user-1");
        userBadge.setBadgeId("badge-1");
        userBadge.setBadgeName("Gold Badge");
        userBadge.setCreatedData(LocalDateTime.of(2026, 1, 1, 0, 0));
        userBadge.setXpAwarded(100);

        userBadgeDto = new UserBadgeDto();
        userBadgeDto.setUserBadgeId("ub-1");
        userBadgeDto.setUserId("user-1");
        userBadgeDto.setBadgeId("badge-1");
        userBadgeDto.setBadgeName("Gold Badge");
        userBadgeDto.setCreatedData(LocalDateTime.of(2026, 1, 1, 0, 0));
        userBadgeDto.setXpAwarded(100);
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createUserBadgeFromDto_shouldReturn200() throws Exception {
        when(userBadgeService.createUserBadge(any(UserBadgeDto.class))).thenReturn("User Badge Created Successfully");

        mockMvc.perform(post("/api/user-badge/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBadgeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Badge Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createUserBadge_shouldReturn200() throws Exception {
        when(userBadgeService.create(any(UserBadge.class))).thenReturn("User Badge Created Successfully");

        mockMvc.perform(post("/api/user-badge/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBadge)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Badge Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllUserBadges_shouldReturn200WithList() throws Exception {
        when(userBadgeService.getAllUserBadge()).thenReturn(List.of(userBadgeDto));

        mockMvc.perform(get("/api/user-badge/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value("user-1"))
                .andExpect(jsonPath("$[0].badgeName").value("Gold Badge"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateUserBadge_shouldReturn200() throws Exception {
        when(userBadgeService.updateUserBadge(eq("ub-1"), any(UserBadge.class)))
                .thenReturn("User Badge Updated Successfully");

        mockMvc.perform(put("/api/user-badge/ub-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBadge)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Badge Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteUserBadge_shouldReturn200() throws Exception {
        when(userBadgeService.deleteUserBadge("ub-1")).thenReturn("User Badge Deleted Successfully");

        mockMvc.perform(delete("/api/user-badge/ub-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("User Badge Deleted Successfully"));
    }
}
