package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.LeaderBoardController;
import com.gamification.streaks.dto.LeaderBoardEntryDto;
import com.gamification.streaks.model.LeaderBoardEntry;
import com.gamification.streaks.service.LeaderBoardService;
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

@WebMvcTest(LeaderBoardController.class)
class LeaderBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaderBoardService leaderBoardService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private LeaderBoardEntry entry;
    private LeaderBoardEntryDto entryDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        entry = new LeaderBoardEntry();
        entry.setLeaderboardId("lb-1");
        entry.setLeaderboardType("WEEKLY");
        entry.setUserId("user-1");
        entry.setXpScore(1500);
        entry.setRank(1);
        entry.setPeriodStart(LocalDate.of(2026, 1, 1));
        entry.setPeriodEnd(LocalDate.of(2026, 1, 7));
        entry.setUpdatedAt(LocalDateTime.of(2026, 1, 7, 0, 0));

        entryDto = new LeaderBoardEntryDto();
        entryDto.setLeaderboardId("lb-1");
        entryDto.setLeaderboardType("WEEKLY");
        entryDto.setUserId("user-1");
        entryDto.setXpScore(1500);
        entryDto.setRank(1);
        entryDto.setPeriodStart(LocalDate.of(2026, 1, 1));
        entryDto.setPeriodEnd(LocalDate.of(2026, 1, 7));
        entryDto.setUpdatedAt(LocalDateTime.of(2026, 1, 7, 0, 0));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createLeaderBoardFromDto_shouldReturn200WithEntry() throws Exception {
        when(leaderBoardService.createLeaderBoard(any(LeaderBoardEntryDto.class))).thenReturn(entry);

        mockMvc.perform(post("/api/leaderboard/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leaderboardId").value("lb-1"))
                .andExpect(jsonPath("$.userId").value("user-1"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createLeaderBoard_shouldReturn200() throws Exception {
        when(leaderBoardService.createLeaderBoard(any(LeaderBoardEntry.class)))
                .thenReturn("LeaderBoard Entry Created Successfully");

        mockMvc.perform(post("/api/leaderboard/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk())
                .andExpect(content().string("LeaderBoard Entry Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllLeaderBoard_shouldReturn200WithList() throws Exception {
        when(leaderBoardService.getAllLeaderBoard()).thenReturn(List.of(entryDto));

        mockMvc.perform(get("/api/leaderboard/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].xpScore").value(1500))
                .andExpect(jsonPath("$[0].rank").value(1));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateLeaderBoard_shouldReturn200() throws Exception {
        when(leaderBoardService.updateLeaderBoard(eq("lb-1"), any(LeaderBoardEntry.class)))
                .thenReturn("LeaderBoard Updated Successfully");

        mockMvc.perform(put("/api/leaderboard/lb-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk())
                .andExpect(content().string("LeaderBoard Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteLeaderBoard_shouldReturn200() throws Exception {
        when(leaderBoardService.deleteLeaderBoard("lb-1")).thenReturn("LeaderBoard Deleted Successfully");

        mockMvc.perform(delete("/api/leaderboard/lb-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("LeaderBoard Deleted Successfully"));
    }
}
