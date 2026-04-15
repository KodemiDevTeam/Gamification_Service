package com.gamification.streaks.service;

import com.gamification.streaks.dto.ChallengeDto;
import com.gamification.streaks.model.Challenge;
import com.gamification.streaks.repository.ChallengeRepository;
import com.gamification.streaks.service.impl.ChallengeServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private ChallengeServiceimpl challengeService;

    private Challenge challenge;
    private ChallengeDto challengeDto;

    @BeforeEach
    void setUp() {
        challenge = new Challenge();
        challenge.setChallengeId("ch-1");
        challenge.setChallengeName("7-Day Streak");
        challenge.setChallengeType("STREAK");
        challenge.setDescription("Complete 7 days in a row");
        challenge.setRewardXP(500);
        challenge.setRewardBadge("streak-badge");
        challenge.setStartDate(LocalDate.of(2026, 1, 1));
        challenge.setEndDate(LocalDate.of(2026, 1, 31));
        challenge.setActive(true);

        challengeDto = new ChallengeDto();
        challengeDto.setChallengeName("7-Day Streak");
        challengeDto.setChallengeType("STREAK");
        challengeDto.setDescription("Complete 7 days in a row");
        challengeDto.setRewardXP(500);
        challengeDto.setRewardBadge("streak-badge");
        challengeDto.setStartDate(LocalDate.of(2026, 1, 1));
        challengeDto.setEndDate(LocalDate.of(2026, 1, 31));
        challengeDto.setActive(true);
    }

    @Test
    void createChallenge_shouldReturnSuccess() {
        // createChallenge(ChallengeDto) maps dto -> Challenge, assigns UUID, saves
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        String result = challengeService.createChallenge(challengeDto);

        assertThat(result).isEqualTo("Challenge Created Successfully");
        verify(challengeRepository).save(any(Challenge.class));
    }

    @Test
    void create_shouldAssignUuidAndReturnSuccess() {
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        String result = challengeService.create(challenge);

        assertThat(result).isEqualTo("Challenge Created Successfully");
        assertThat(challenge.getChallengeId()).isNotNull();
        verify(challengeRepository).save(challenge);
    }

    @Test
    void getAllChallenge_shouldReturnMappedDtoList() {
        when(challengeRepository.findAll()).thenReturn(List.of(challenge));

        List<ChallengeDto> result = challengeService.getAllChallenge();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getChallengeName()).isEqualTo("7-Day Streak");
        assertThat(result.get(0).getRewardXP()).isEqualTo(500);
    }

    @Test
    void getAllChallenge_shouldReturnEmptyList_whenNoChallenges() {
        when(challengeRepository.findAll()).thenReturn(List.of());

        assertThat(challengeService.getAllChallenge()).isEmpty();
    }

    @Test
    void updateChallenge_shouldUpdateAndReturnSuccess() {
        Challenge updated = new Challenge();
        updated.setChallengeName("30-Day Streak");
        updated.setChallengeType("PERFORMANCE");
        updated.setDescription("Updated description");
        updated.setRewardXP(1000);
        updated.setRewardBadge("gold-badge");
        updated.setStartDate(LocalDate.of(2026, 2, 1));
        updated.setEndDate(LocalDate.of(2026, 2, 28));
        updated.setActive(false);

        when(challengeRepository.findById("ch-1")).thenReturn(challenge);
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        String result = challengeService.updateChallenge("ch-1", updated);

        assertThat(result).isEqualTo("Challenge Updated Successfully");
        assertThat(challenge.getChallengeName()).isEqualTo("30-Day Streak");
        verify(challengeRepository).save(challenge);
    }

    @Test
    void updateChallenge_shouldThrow_whenNotFound() {
        when(challengeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> challengeService.updateChallenge("missing", challenge))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Challenge Not Found");
    }

    @Test
    void deleteChallenge_shouldReturnSuccess_whenExists() {
        when(challengeRepository.findById("ch-1")).thenReturn(challenge);

        String result = challengeService.deleteChallenge("ch-1");

        assertThat(result).isEqualTo("Challenge Deleted Successfully");
        verify(challengeRepository).delete("ch-1");
    }

    @Test
    void deleteChallenge_shouldThrow_whenNotFound() {
        when(challengeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> challengeService.deleteChallenge("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Challenge Not Found");
    }
}
