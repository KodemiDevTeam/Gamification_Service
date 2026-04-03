package com.gamification.streaks.service;

import com.gamification.streaks.dto.StreakRecordDto;
import com.gamification.streaks.model.StreakRecord;
import com.gamification.streaks.repository.StreakRecordRepository;
import com.gamification.streaks.service.Impl.StreakRecordServiceImpl;
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
class StreakRecordServiceImplTest {

    @Mock
    private StreakRecordRepository streakRecordRepository;

    @InjectMocks
    private StreakRecordServiceImpl streakRecordService;

    private StreakRecord streakRecord;
    private StreakRecordDto streakRecordDto;

    @BeforeEach
    void setUp() {
        streakRecord = new StreakRecord();
        streakRecord.setStreakId("streak-1");
        streakRecord.setUserId("user-1");
        streakRecord.setStreakType("DAILY");
        streakRecord.setCurrentStreakCount(7);
        streakRecord.setLongestStreakCount(14);
        streakRecord.setLastActiveData(LocalDate.now());
        streakRecord.setStreakStatus("ACTIVE");
        streakRecord.setCreatedAt(LocalDateTime.now());
        streakRecord.setUpdatedAt(LocalDateTime.now());

        streakRecordDto = new StreakRecordDto();
        streakRecordDto.setUserId("user-1");
        streakRecordDto.setStreakType("DAILY");
        streakRecordDto.setCurrentStreakCount(7);
        streakRecordDto.setLongestStreakCount(14);
        streakRecordDto.setLastActiveData(LocalDate.now());
        streakRecordDto.setStreakStatus("ACTIVE");
        streakRecordDto.setCreatedAt(LocalDateTime.now());
        streakRecordDto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createStreakRecord_shouldReturnSuccess() {
        when(streakRecordRepository.save(any(StreakRecord.class))).thenReturn(streakRecord);

        String result = streakRecordService.createStreakRecord(streakRecordDto);

        assertThat(result).isEqualTo("Streak Record Created Successfully");
        verify(streakRecordRepository).save(any(StreakRecord.class));
    }

    @Test
    void create_shouldAssignUuidAndReturnSuccess() {
        when(streakRecordRepository.save(any(StreakRecord.class))).thenReturn(streakRecord);

        String result = streakRecordService.create(streakRecord);

        assertThat(result).isEqualTo("Streak Record Created Successfully");
        assertThat(streakRecord.getStreakId()).isNotNull();
        verify(streakRecordRepository).save(streakRecord);
    }

    @Test
    void getAllStreakRecord_shouldReturnMappedDtoList() {
        when(streakRecordRepository.findAll()).thenReturn(List.of(streakRecord));

        List<StreakRecordDto> result = streakRecordService.getAllStreakRecord();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo("user-1");
        assertThat(result.get(0).getCurrentStreakCount()).isEqualTo(7);
        assertThat(result.get(0).getStreakStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void getAllStreakRecord_shouldReturnEmptyList_whenNoRecords() {
        when(streakRecordRepository.findAll()).thenReturn(List.of());

        assertThat(streakRecordService.getAllStreakRecord()).isEmpty();
    }

    @Test
    void updateStreak_shouldUpdateFieldsAndReturnSuccess() {
        StreakRecord updated = new StreakRecord();
        updated.setUserId("user-2");
        updated.setStreakType("WEEKLY");
        updated.setCurrentStreakCount(3);
        updated.setLongestStreakCount(10);
        updated.setLastActiveData(LocalDate.now().minusDays(1));
        updated.setStreakStatus("BROKEN");
        updated.setUpdatedAt(LocalDateTime.now());

        when(streakRecordRepository.findById("streak-1")).thenReturn(streakRecord);
        when(streakRecordRepository.save(any(StreakRecord.class))).thenReturn(streakRecord);

        String result = streakRecordService.updateStreak("streak-1", updated);

        assertThat(result).isEqualTo("Streak Record Updated Successfully");
        assertThat(streakRecord.getStreakStatus()).isEqualTo("BROKEN");
        assertThat(streakRecord.getCurrentStreakCount()).isEqualTo(3);
        verify(streakRecordRepository).save(streakRecord);
    }

    @Test
    void updateStreak_shouldThrow_whenNotFound() {
        when(streakRecordRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> streakRecordService.updateStreak("missing", streakRecord))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Streak Record Not Found");
    }

    @Test
    void deleteStreak_shouldReturnSuccess_whenExists() {
        when(streakRecordRepository.findById("streak-1")).thenReturn(streakRecord);

        String result = streakRecordService.deleteStreak("streak-1");

        assertThat(result).isEqualTo("Streak Record Deleted Successfully");
        verify(streakRecordRepository).delete("streak-1");
    }

    @Test
    void deleteStreak_shouldThrow_whenNotFound() {
        when(streakRecordRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> streakRecordService.deleteStreak("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Streak Record Not Found");
    }
}
