package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.StreakRecordController;
import com.gamification.streaks.dto.StreakRecordDto;
import com.gamification.streaks.model.StreakRecord;
import com.gamification.streaks.service.StreakRecordService;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StreakRecordController.class)
class StreakRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StreakRecordService streakRecordService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private StreakRecord streakRecord;
    private StreakRecordDto streakRecordDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        streakRecord = new StreakRecord();
        streakRecord.setStreakId("streak-1");
        streakRecord.setUserId("user-1");
        streakRecord.setStreakType("DAILY");
        streakRecord.setCurrentStreakCount(7);
        streakRecord.setLongestStreakCount(14);
        streakRecord.setLastActiveData(LocalDate.of(2026, 1, 1));
        streakRecord.setStreakStatus("ACTIVE");
        streakRecord.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
        streakRecord.setUpdatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));

        streakRecordDto = new StreakRecordDto();
        streakRecordDto.setStreakId("streak-1");
        streakRecordDto.setUserId("user-1");
        streakRecordDto.setStreakType("DAILY");
        streakRecordDto.setCurrentStreakCount(7);
        streakRecordDto.setLongestStreakCount(14);
        streakRecordDto.setLastActiveData(LocalDate.of(2026, 1, 1));
        streakRecordDto.setStreakStatus("ACTIVE");
        streakRecordDto.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
        streakRecordDto.setUpdatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createStreakRecordFromDto_shouldReturn200() throws Exception {
        when(streakRecordService.createStreakRecord(any(StreakRecordDto.class)))
                .thenReturn("Streak Record Created Successfully");

        mockMvc.perform(post("/api/streak-record/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(streakRecordDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Streak Record Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createStreakRecord_shouldReturn200() throws Exception {
        when(streakRecordService.create(any(StreakRecord.class))).thenReturn("Streak Record Created Successfully");

        mockMvc.perform(post("/api/streak-record/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(streakRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("Streak Record Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllStreakRecords_shouldReturn200WithList() throws Exception {
        when(streakRecordService.getAllStreakRecord()).thenReturn(List.of(streakRecordDto));

        mockMvc.perform(get("/api/streak-record/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value("user-1"))
                .andExpect(jsonPath("$[0].currentStreakCount").value(7));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateStreak_shouldReturn200() throws Exception {
        when(streakRecordService.updateStreak(eq("streak-1"), any(StreakRecord.class)))
                .thenReturn("Streak Record Updated Successfully");

        mockMvc.perform(put("/api/streak-record/streak-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(streakRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("Streak Record Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteStreak_shouldReturn200() throws Exception {
        when(streakRecordService.deleteStreak("streak-1")).thenReturn("Streak Record Deleted Successfully");

        mockMvc.perform(delete("/api/streak-record/streak-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Streak Record Deleted Successfully"));
    }
}
