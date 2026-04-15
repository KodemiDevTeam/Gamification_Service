package com.gamification.streaks.service;

import com.gamification.streaks.dto.RewardRuleDto;
import com.gamification.streaks.model.RewardRule;
import com.gamification.streaks.repository.RewardRuleRepository;
import com.gamification.streaks.service.impl.RewardRuleServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardRuleServiceImplTest {

    @Mock
    private RewardRuleRepository rewardRuleRepository;

    @InjectMocks
    private RewardRuleServiceimpl rewardRuleService;

    private RewardRule rewardRule;
    private RewardRuleDto rewardRuleDto;

    @BeforeEach
    void setUp() {
        rewardRule = new RewardRule();
        rewardRule.setRuleId("rr-1");
        rewardRule.setRuleName("XP Reward");
        rewardRule.setRuleType("XP");
        rewardRule.setConditionType("STREAK_COUNT");
        rewardRule.setConditionValue(7);
        rewardRule.setRewardType("XP");
        rewardRule.setRewardValue(100);
        rewardRule.setActive(true);
        rewardRule.setCreatedAt(LocalDateTime.now());

        rewardRuleDto = new RewardRuleDto();
        rewardRuleDto.setRuleName("XP Reward");
        rewardRuleDto.setRuleType("XP");
        rewardRuleDto.setConditionType("STREAK_COUNT");
        rewardRuleDto.setConditionValue(7);
        rewardRuleDto.setRewardType("XP");
        rewardRuleDto.setRewardValue(100);
        rewardRuleDto.setActive(true);
        rewardRuleDto.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createRewardRule_shouldReturnSuccess() {
        when(rewardRuleRepository.save(any(RewardRule.class))).thenReturn(rewardRule);

        String result = rewardRuleService.createRewardRule(rewardRuleDto);

        assertThat(result).isEqualTo("Reward Rule Created Successfully");
        verify(rewardRuleRepository).save(any(RewardRule.class));
    }

    @Test
    void create_shouldAssignUuidAndReturnSuccess() {
        when(rewardRuleRepository.save(any(RewardRule.class))).thenReturn(rewardRule);

        String result = rewardRuleService.create(rewardRule);

        assertThat(result).isEqualTo("Reward Rule Created Successfully");
        assertThat(rewardRule.getRuleId()).isNotNull();
        verify(rewardRuleRepository).save(rewardRule);
    }

    @Test
    void getAllRewardRule_shouldReturnMappedDtoList() {
        when(rewardRuleRepository.findAll()).thenReturn(List.of(rewardRule));

        List<RewardRuleDto> result = rewardRuleService.getAllRewardRule();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRuleName()).isEqualTo("XP Reward");
        assertThat(result.get(0).getConditionValue()).isEqualTo(7);
    }

    @Test
    void getAllRewardRule_shouldReturnEmptyList_whenNoRules() {
        when(rewardRuleRepository.findAll()).thenReturn(List.of());

        assertThat(rewardRuleService.getAllRewardRule()).isEmpty();
    }

    @Test
    void updateRewardRule_shouldUpdateAndReturnSuccess() {
        RewardRule updated = new RewardRule();
        updated.setRuleName("Badge Reward");
        updated.setRuleType("BADGE");
        updated.setConditionType("BADGE_COUNT");
        updated.setConditionValue(5);
        updated.setRewardType("BADGE");
        updated.setRewardValue(1);
        updated.setActive(false);

        when(rewardRuleRepository.findById("rr-1")).thenReturn(rewardRule);
        when(rewardRuleRepository.save(any(RewardRule.class))).thenReturn(rewardRule);

        String result = rewardRuleService.updateRewardRule("rr-1", updated);

        assertThat(result).isEqualTo("Reward Rule Updated Successfully");
        assertThat(rewardRule.getRuleName()).isEqualTo("Badge Reward");
        assertThat(rewardRule.getConditionValue()).isEqualTo(5);
        verify(rewardRuleRepository).save(rewardRule);
    }

    @Test
    void updateRewardRule_shouldThrow_whenNotFound() {
        when(rewardRuleRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> rewardRuleService.updateRewardRule("missing", rewardRule))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reward Rule Not Found");
    }

    @Test
    void deleteRewardRule_shouldReturnSuccess_whenExists() {
        when(rewardRuleRepository.findById("rr-1")).thenReturn(rewardRule);

        String result = rewardRuleService.deleteRewardRule("rr-1");

        assertThat(result).isEqualTo("Reward Rule Deleted Successfully");
        verify(rewardRuleRepository).delete("rr-1");
    }

    @Test
    void deleteRewardRule_shouldThrow_whenNotFound() {
        when(rewardRuleRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> rewardRuleService.deleteRewardRule("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reward Rule Not Found");
    }
}
