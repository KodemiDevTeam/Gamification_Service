package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.UserGamificationProfileController;
import com.gamification.streaks.dto.UserGamificationProfileDto;
import com.gamification.streaks.model.UserGamificationProfile;
import com.gamification.streaks.service.UserGamificationProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserGamificationProfileController.class)
class UserGamificationProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserGamificationProfileService userGamificationProfileService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private UserGamificationProfile profile;
    private UserGamificationProfileDto profileDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        profile = new UserGamificationProfile();
        profile.setUserId("user-1");
        profile.setRole("LEARNER");
        profile.setTotalXp(500);
        profile.setCurrentLevel(3);
        profile.setLongestStreak(10);
        profile.setLastActivityData(LocalDate.of(2026, 1, 1));
        profile.setTotalBadges(5);
        profile.setTotalReward(2);
        profile.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
        profile.setUpdatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));

        profileDto = new UserGamificationProfileDto();
        profileDto.setUserId("user-1");
        profileDto.setRole("LEARNER");
        profileDto.setTotalXp(500);
        profileDto.setCurrentLevel(3);
        profileDto.setLongestStreak(10);
        profileDto.setLastActivityData(LocalDate.of(2026, 1, 1));
        profileDto.setTotalBadges(5);
        profileDto.setTotalReward(2);
        profileDto.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
        profileDto.setUpdatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createUserGamificationFromDto_shouldReturn200WithProfile() throws Exception {
        when(userGamificationProfileService.createUserGamification(any(UserGamificationProfileDto.class)))
                .thenReturn(profile);

        mockMvc.perform(post("/api/user-gamification/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user-1"))
                .andExpect(jsonPath("$.totalXp").value(500));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createUserGamificationProfile_shouldReturn200() throws Exception {
        when(userGamificationProfileService.createUserGamificationProfile(any(UserGamificationProfile.class)))
                .thenReturn("User Gamification Profile Created Successfully");

        mockMvc.perform(post("/api/user-gamification/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Gamification Profile Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getUserGamificationProfile_shouldReturn200WithDto() throws Exception {
        when(userGamificationProfileService.getUserGamificationProfileById("user-1")).thenReturn(profileDto);

        mockMvc.perform(get("/api/user-gamification/user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user-1"))
                .andExpect(jsonPath("$.currentLevel").value(3))
                .andExpect(jsonPath("$.totalBadges").value(5));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllUserGamificationProfiles_shouldReturn200WithList() throws Exception {
        when(userGamificationProfileService.getAllUserGamificationProfiles()).thenReturn(List.of(profileDto));

        mockMvc.perform(get("/api/user-gamification/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].role").value("LEARNER"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateUserGamificationProfile_shouldReturn200() throws Exception {
        when(userGamificationProfileService.updateUserGamificationProfile(eq("user-1"), any(UserGamificationProfile.class)))
                .thenReturn("User Gamification Profile Updated Successfully");

        mockMvc.perform(put("/api/user-gamification/user-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Gamification Profile Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteUserGamificationProfile_shouldReturn200() throws Exception {
        when(userGamificationProfileService.deleteUserGamificationProfile("user-1"))
                .thenReturn("User Gamification Profile Deleted Successfully");

        mockMvc.perform(delete("/api/user-gamification/user-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("User Gamification Profile Deleted Successfully"));
    }
}
