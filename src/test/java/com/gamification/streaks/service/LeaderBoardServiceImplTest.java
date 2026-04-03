package com.gamification.streaks.service;

import com.gamification.streaks.dto.LeaderBoardEntryDto;
import com.gamification.streaks.model.LeaderBoardEntry;
import com.gamification.streaks.repository.LeaderBoardRepository;
import com.gamification.streaks.service.Impl.LeaderBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceImplTest {

    @Mock
    private LeaderBoardRepository leaderBoardRepository;

    @InjectMocks
    private LeaderBoardServiceImpl leaderBoardService;

    private LeaderBoardEntry entry;
    private LeaderBoardEntryDto entryDto;

    @BeforeEach
    void setUp() {
        entry = new LeaderBoardEntry();
        entry.setLeaderboardId("lb-1");
        entry.setLeaderboardType("WEEKLY");
        entry.setUserId("user-1");
        entry.setXpScore(1500);
        entry.setRank(1);
        entry.setPeriodStart(LocalDate.of(2026, 1, 1));
        entry.setPeriodEnd(LocalDate.of(2026, 1, 7));
        entry.setUpdatedAt(LocalDateTime.now());

        entryDto = new LeaderBoardEntryDto();
        entryDto.setLeaderboardType("WEEKLY");
        entryDto.setUserId("user-1");
        entryDto.setXpScore(1500);
        entryDto.setRank(1);
        entryDto.setPeriodStart(LocalDate.of(2026, 1, 1));
        entryDto.setPeriodEnd(LocalDate.of(2026, 1, 7));
        entryDto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createLeaderBoard_fromDto_shouldReturnSavedEntry() {
        // createLeaderBoard(LeaderBoardEntryDto) maps dto -> entry, assigns UUID, saves and returns it
        when(leaderBoardRepository.save(any(LeaderBoardEntry.class))).thenAnswer(inv -> inv.getArgument(0));

        LeaderBoardEntry result = leaderBoardService.createLeaderBoard(entryDto);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo("user-1");
        assertThat(result.getXpScore()).isEqualTo(1500);
        assertThat(result.getLeaderboardId()).isNotNull(); // UUID assigned
        verify(leaderBoardRepository).save(any(LeaderBoardEntry.class));
    }

    @Test
    void createLeaderBoard_fromEntity_shouldReturnSuccess() {
        when(leaderBoardRepository.save(any(LeaderBoardEntry.class))).thenReturn(entry);

        String result = leaderBoardService.createLeaderBoard(entry);

        assertThat(result).isEqualTo("LeaderBoard Entry Created Successfully");
        assertThat(entry.getLeaderboardId()).isNotNull();
        verify(leaderBoardRepository).save(entry);
    }

    @Test
    void getAllLeaderBoard_shouldReturnMappedDtoList() {
        when(leaderBoardRepository.findAll()).thenReturn(List.of(entry));

        List<LeaderBoardEntryDto> result = leaderBoardService.getAllLeaderBoard();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo("user-1");
        assertThat(result.get(0).getRank()).isEqualTo(1);
    }

    @Test
    void getAllLeaderBoard_shouldReturnEmptyList_whenNoEntries() {
        when(leaderBoardRepository.findAll()).thenReturn(List.of());

        assertThat(leaderBoardService.getAllLeaderBoard()).isEmpty();
    }

    @Test
    void updateLeaderBoard_shouldUpdateFieldsAndReturnSuccess() {
        LeaderBoardEntry updated = new LeaderBoardEntry();
        updated.setLeaderboardType("MONTHLY");
        updated.setUserId("user-2");
        updated.setXpScore(3000);
        updated.setRank(2);
        updated.setPeriodStart(LocalDate.of(2026, 2, 1));
        updated.setPeriodEnd(LocalDate.of(2026, 2, 28));
        updated.setUpdatedAt(LocalDateTime.now());

        when(leaderBoardRepository.findById("lb-1")).thenReturn(entry);
        when(leaderBoardRepository.save(any(LeaderBoardEntry.class))).thenReturn(entry);

        String result = leaderBoardService.updateLeaderBoard("lb-1", updated);

        assertThat(result).isEqualTo("LeaderBoard Updated Successfully");
        assertThat(entry.getLeaderboardType()).isEqualTo("MONTHLY");
        assertThat(entry.getXpScore()).isEqualTo(3000);
        verify(leaderBoardRepository).save(entry);
    }

    @Test
    void updateLeaderBoard_shouldThrow_whenNotFound() {
        when(leaderBoardRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> leaderBoardService.updateLeaderBoard("missing", entry))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("LeaderBoard Entry Not Found");
    }

    @Test
    void deleteLeaderBoard_shouldReturnSuccess_whenExists() {
        when(leaderBoardRepository.findById("lb-1")).thenReturn(entry);

        String result = leaderBoardService.deleteLeaderBoard("lb-1");

        assertThat(result).isEqualTo("LeaderBoard Deleted Successfully");
        verify(leaderBoardRepository).delete("lb-1");
    }

    @Test
    void deleteLeaderBoard_shouldThrow_whenNotFound() {
        when(leaderBoardRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> leaderBoardService.deleteLeaderBoard("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("LeaderBoard Entry Not Found");
    }
}
