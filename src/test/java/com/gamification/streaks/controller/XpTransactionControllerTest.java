package com.gamification.streaks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.streaks.config.JwtFilter;
import com.gamification.streaks.config.JwtUtil;
import com.gamification.streaks.controllers.XpTransactionController;
import com.gamification.streaks.dto.XpTransactionDto;
import com.gamification.streaks.model.XpTransaction;
import com.gamification.streaks.service.XpTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(XpTransactionController.class)
class XpTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private XpTransactionService xpTransactionService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private XpTransaction xpTransaction;
    private XpTransactionDto xpTransactionDto;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(inv -> {
            ((FilterChain) inv.getArgument(2)).doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtFilter).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class), any(FilterChain.class));

        xpTransaction = new XpTransaction();
        xpTransaction.setXpTransactionId("xp-1");
        xpTransaction.setUserId("user-1");
        xpTransaction.setXpAmount(200);
        xpTransaction.setXpSource("QUIZ_COMPLETION");
        xpTransaction.setReferenceId("quiz-101");
        xpTransaction.setDescription("Completed quiz 101");
        xpTransaction.setCreatedAt("2026-01-01T10:00:00");

        xpTransactionDto = new XpTransactionDto();
        xpTransactionDto.setXpTransactionId("xp-1");
        xpTransactionDto.setUserId("user-1");
        xpTransactionDto.setXpAmount(200);
        xpTransactionDto.setXpSource("QUIZ_COMPLETION");
        xpTransactionDto.setReferenceId("quiz-101");
        xpTransactionDto.setDescription("Completed quiz 101");
        xpTransactionDto.setCreatedAt("2026-01-01T10:00:00");
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createXpTransactionFromDto_shouldReturn200() throws Exception {
        when(xpTransactionService.createXpTransaction(any(XpTransactionDto.class)))
                .thenReturn("XP Transaction Created Successfully");

        mockMvc.perform(post("/api/xp-transaction/create-dto")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(xpTransactionDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("XP Transaction Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void createXpTransaction_shouldReturn200() throws Exception {
        when(xpTransactionService.create(any(XpTransaction.class))).thenReturn("XP Transaction Created Successfully");

        mockMvc.perform(post("/api/xp-transaction/create")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(xpTransaction)))
                .andExpect(status().isOk())
                .andExpect(content().string("XP Transaction Created Successfully"));
    }

    @Test
    @WithMockUser(roles = "LEARNER")
    void getAllXpTransactions_shouldReturn200WithList() throws Exception {
        when(xpTransactionService.getAllXpTransaction()).thenReturn(List.of(xpTransactionDto));

        mockMvc.perform(get("/api/xp-transaction/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value("user-1"))
                .andExpect(jsonPath("$[0].xpAmount").value(200));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void updateXpTransaction_shouldReturn200() throws Exception {
        when(xpTransactionService.update(eq("xp-1"), any(XpTransaction.class)))
                .thenReturn("XP Transaction Updated Successfully");

        mockMvc.perform(put("/api/xp-transaction/xp-1")
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(xpTransaction)))
                .andExpect(status().isOk())
                .andExpect(content().string("XP Transaction Updated Successfully"));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void deleteXpTransaction_shouldReturn200() throws Exception {
        when(xpTransactionService.deleteXpTransaction("xp-1")).thenReturn("XP Transaction Deleted Successfully");

        mockMvc.perform(delete("/api/xp-transaction/xp-1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("XP Transaction Deleted Successfully"));
    }
}
