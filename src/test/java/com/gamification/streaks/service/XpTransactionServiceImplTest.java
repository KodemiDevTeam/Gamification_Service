package com.gamification.streaks.service;

import com.gamification.streaks.dto.XpTransactionDto;
import com.gamification.streaks.model.XpTransaction;
import com.gamification.streaks.repository.XpTransactionRepository;
import com.gamification.streaks.service.Impl.XpTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class XpTransactionServiceImplTest {

    @Mock
    private XpTransactionRepository xpTransactionRepository;

    @InjectMocks
    private XpTransactionServiceImpl xpTransactionService;

    private XpTransaction xpTransaction;
    private XpTransactionDto xpTransactionDto;

    @BeforeEach
    void setUp() {
        xpTransaction = new XpTransaction();
        xpTransaction.setXpTransactionId("xp-1");
        xpTransaction.setUserId("user-1");
        xpTransaction.setXpAmount(200);
        xpTransaction.setXpSource("QUIZ_COMPLETION");
        xpTransaction.setReferenceId("quiz-101");
        xpTransaction.setDescription("Completed quiz 101");
        xpTransaction.setCreatedAt("2026-01-01T10:00:00");

        xpTransactionDto = new XpTransactionDto();
        xpTransactionDto.setUserId("user-1");
        xpTransactionDto.setXpAmount(200);
        xpTransactionDto.setXpSource("QUIZ_COMPLETION");
        xpTransactionDto.setReferenceId("quiz-101");
        xpTransactionDto.setDescription("Completed quiz 101");
        xpTransactionDto.setCreatedAt("2026-01-01T10:00:00");
    }

    @Test
    void createXpTransaction_shouldReturnSuccess() {
        when(xpTransactionRepository.save(any(XpTransaction.class))).thenReturn(xpTransaction);

        String result = xpTransactionService.createXpTransaction(xpTransactionDto);

        assertThat(result).isEqualTo("XP Transaction Created Successfully");
        verify(xpTransactionRepository).save(any(XpTransaction.class));
    }

    @Test
    void create_shouldAssignUuidAndReturnSuccess() {
        when(xpTransactionRepository.save(any(XpTransaction.class))).thenReturn(xpTransaction);

        String result = xpTransactionService.create(xpTransaction);

        assertThat(result).isEqualTo("XP Transaction Created Successfully");
        assertThat(xpTransaction.getXpTransactionId()).isNotNull();
        verify(xpTransactionRepository).save(xpTransaction);
    }

    @Test
    void getAllXpTransaction_shouldReturnMappedDtoList() {
        when(xpTransactionRepository.findAll()).thenReturn(List.of(xpTransaction));

        List<XpTransactionDto> result = xpTransactionService.getAllXpTransaction();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo("user-1");
        assertThat(result.get(0).getXpAmount()).isEqualTo(200);
        assertThat(result.get(0).getXpSource()).isEqualTo("QUIZ_COMPLETION");
    }

    @Test
    void getAllXpTransaction_shouldReturnEmptyList_whenNoTransactions() {
        when(xpTransactionRepository.findAll()).thenReturn(List.of());

        assertThat(xpTransactionService.getAllXpTransaction()).isEmpty();
    }

    @Test
    void update_shouldUpdateFieldsAndReturnSuccess() {
        XpTransaction updated = new XpTransaction();
        updated.setUserId("user-2");
        updated.setXpAmount(500);
        updated.setXpSource("COURSE_COMPLETION");
        updated.setReferenceId("course-202");
        updated.setDescription("Completed course 202");
        updated.setCreatedAt("2026-02-01T12:00:00");

        when(xpTransactionRepository.findById("xp-1")).thenReturn(xpTransaction);
        when(xpTransactionRepository.save(any(XpTransaction.class))).thenReturn(xpTransaction);

        String result = xpTransactionService.update("xp-1", updated);

        assertThat(result).isEqualTo("XP Transaction Updated Successfully");
        assertThat(xpTransaction.getXpAmount()).isEqualTo(500);
        assertThat(xpTransaction.getXpSource()).isEqualTo("COURSE_COMPLETION");
        verify(xpTransactionRepository).save(xpTransaction);
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        when(xpTransactionRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> xpTransactionService.update("missing", xpTransaction))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("XP Transaction Not Found");
    }

    @Test
    void deleteXpTransaction_shouldReturnSuccess_whenExists() {
        when(xpTransactionRepository.findById("xp-1")).thenReturn(xpTransaction);

        String result = xpTransactionService.deleteXpTransaction("xp-1");

        assertThat(result).isEqualTo("XP Transaction Deleted Successfully");
        verify(xpTransactionRepository).delete("xp-1");
    }

    @Test
    void deleteXpTransaction_shouldThrow_whenNotFound() {
        when(xpTransactionRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> xpTransactionService.deleteXpTransaction("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("XP Transaction Not Found");
    }
}
