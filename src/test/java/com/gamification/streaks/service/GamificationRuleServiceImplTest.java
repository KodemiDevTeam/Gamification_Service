package com.gamification.streaks.service;

import com.gamification.streaks.dto.GamificationRuleDto;
import com.gamification.streaks.model.GamificationRule;
import com.gamification.streaks.repository.GamificationRuleRepository;
import com.gamification.streaks.service.Impl.GamificationRuleServiceImpl;
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
class GamificationRuleServiceImplTest {

    @Mock
    private GamificationRuleRepository gamificationRuleRepository;

    @InjectMocks
    private GamificationRuleServiceImpl gamificationRuleService;

    private GamificationRule rule;
    private GamificationRuleDto ruleDto;

    @BeforeEach
    void setUp() {
        rule = new GamificationRule();
        rule.setRuleId("rule-1");
        rule.setRuleName("XP Rule");
        rule.setRuleType("XP");
        rule.setConfig("{\"multiplier\": 2}");
        rule.setEnabled(true);
        rule.setVersion(1);
        rule.setAbCohort("A");
        rule.setCreatedAt("2026-01-01");
        rule.setUpdatedAt("2026-01-01");
        rule.setUpdatedBy("admin");
        rule.setRollbackFromRuleId(null);

        ruleDto = new GamificationRuleDto();
        ruleDto.setRuleName("XP Rule");
        ruleDto.setRuleType("XP");
        ruleDto.setConfig("{\"multiplier\": 2}");
        ruleDto.setEnabled(true);
        ruleDto.setVersion(1);
        ruleDto.setAbCohort("A");
        ruleDto.setCreatedAt("2026-01-01");
        ruleDto.setUpdatedAt("2026-01-01");
        ruleDto.setUpdatedBy("admin");
        ruleDto.setRollbackFromRuleId(null);
    }

    @Test
    void createGamificationRule_shouldReturnSuccess() {
        // Maps dto -> GamificationRule, assigns UUID, saves
        when(gamificationRuleRepository.save(any(GamificationRule.class))).thenReturn(rule);

        String result = gamificationRuleService.createGamificationRule(ruleDto);

        assertThat(result).isEqualTo("Gamification Rule Created Successfully");
        verify(gamificationRuleRepository).save(any(GamificationRule.class));
    }

    @Test
    void create_shouldAssignUuidAndReturnSuccess() {
        when(gamificationRuleRepository.save(any(GamificationRule.class))).thenReturn(rule);

        String result = gamificationRuleService.create(rule);

        assertThat(result).isEqualTo("Gamification Rule Created Successfully");
        assertThat(rule.getRuleId()).isNotNull();
        verify(gamificationRuleRepository).save(rule);
    }

    @Test
    void update_shouldUpdateFieldsAndReturnAllRules() {
        // update() updates the rule and returns the full list as DTOs
        GamificationRule updatedRule = new GamificationRule();
        updatedRule.setRuleName("Updated Rule");
        updatedRule.setRuleType("BADGE");
        updatedRule.setConfig("{\"threshold\": 10}");
        updatedRule.setEnabled(false);
        updatedRule.setVersion(2);
        updatedRule.setAbCohort("B");
        updatedRule.setUpdatedAt("2026-02-01");
        updatedRule.setUpdatedBy("trainer");
        updatedRule.setRollbackFromRuleId("rule-0");

        when(gamificationRuleRepository.findById("rule-1")).thenReturn(rule);
        when(gamificationRuleRepository.save(any(GamificationRule.class))).thenReturn(rule);
        when(gamificationRuleRepository.findAll()).thenReturn(List.of(rule));

        List<GamificationRuleDto> result = gamificationRuleService.update("rule-1", updatedRule);

        assertThat(result).hasSize(1);
        assertThat(rule.getRuleName()).isEqualTo("Updated Rule"); // field mutated
        verify(gamificationRuleRepository).save(rule);
    }

    @Test
    void update_shouldThrow_whenRuleNotFound() {
        when(gamificationRuleRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> gamificationRuleService.update("missing", rule))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Gamification Rule Not Found");
    }

    @Test
    void deleteGamificationRule_shouldReturnSuccess_whenExists() {
        when(gamificationRuleRepository.findById("rule-1")).thenReturn(rule);

        String result = gamificationRuleService.deleteGamificationRule("rule-1");

        assertThat(result).isEqualTo("Gamification Rule Deleted Successfully");
        verify(gamificationRuleRepository).delete("rule-1");
    }

    @Test
    void deleteGamificationRule_shouldThrow_whenNotFound() {
        when(gamificationRuleRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> gamificationRuleService.deleteGamificationRule("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Gamification Rule Not Found");
    }
}
