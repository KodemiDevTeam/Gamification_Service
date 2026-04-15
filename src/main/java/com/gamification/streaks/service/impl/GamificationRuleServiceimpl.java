package com.gamification.streaks.service.impl;

import com.gamification.streaks.dto.GamificationRuleDto;
import com.gamification.streaks.execption.ResourceNotFoundException;
import com.gamification.streaks.model.GamificationRule;
import com.gamification.streaks.repository.GamificationRuleRepository;
import com.gamification.streaks.service.GamificationRuleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GamificationRuleServiceimpl implements GamificationRuleService {

    private final GamificationRuleRepository gamificationRuleRepository;

    public GamificationRuleServiceimpl(GamificationRuleRepository gamificationRuleRepository) {
        this.gamificationRuleRepository = gamificationRuleRepository;
    }

    public String createGamificationRule(GamificationRuleDto dto) {
        GamificationRule rule = new GamificationRule();
        rule.setRuleId(UUID.randomUUID().toString());
        rule.setRuleName(dto.getRuleName());
        rule.setRuleType(dto.getRuleType());
        rule.setConfig(dto.getConfig());
        rule.setEnabled(dto.getEnabled());
        rule.setVersion(dto.getVersion());
        rule.setAbCohort(dto.getAbCohort());
        rule.setCreatedAt(LocalDateTime.now().toString());
        rule.setUpdatedAt(LocalDateTime.now().toString());
        rule.setUpdatedBy(dto.getUpdatedBy());
        rule.setRollbackFromRuleId(dto.getRollbackFromRuleId());
        gamificationRuleRepository.save(rule);
        return "Gamification Rule Created Successfully";
    }

    public String create(GamificationRule gamificationRule) {
        gamificationRule.setRuleId(UUID.randomUUID().toString());
        gamificationRuleRepository.save(gamificationRule);
        return "Gamification Rule Created Successfully";
    }

    public List<GamificationRuleDto> update(String ruleId, GamificationRule gamificationRule) {
        GamificationRule existing = gamificationRuleRepository.findById(ruleId);
        if (existing == null) {
            throw new ResourceNotFoundException("Gamification Rule Not Found");
        }
        existing.setRuleName(gamificationRule.getRuleName());
        existing.setRuleType(gamificationRule.getRuleType());
        existing.setConfig(gamificationRule.getConfig());
        existing.setEnabled(gamificationRule.getEnabled());
        existing.setVersion(gamificationRule.getVersion());
        existing.setAbCohort(gamificationRule.getAbCohort());
        existing.setUpdatedAt(LocalDateTime.now().toString());
        existing.setUpdatedBy(gamificationRule.getUpdatedBy());
        existing.setRollbackFromRuleId(gamificationRule.getRollbackFromRuleId());
        gamificationRuleRepository.save(existing);

        List<GamificationRule> rules = gamificationRuleRepository.findAll();
        List<GamificationRuleDto> dtoList = new ArrayList<>();
        for (GamificationRule rule : rules) {
            dtoList.add(mapToDto(rule));
        }
        return dtoList;
    }

    public String deleteGamificationRule(String ruleId) {
        GamificationRule rule = gamificationRuleRepository.findById(ruleId);
        if (rule == null) {
            throw new ResourceNotFoundException("Gamification Rule Not Found");
        }
        gamificationRuleRepository.delete(ruleId);
        return "Gamification Rule Deleted Successfully";
    }

    private GamificationRuleDto mapToDto(GamificationRule rule) {
        GamificationRuleDto dto = new GamificationRuleDto();
        dto.setRuleId(rule.getRuleId());
        dto.setRuleName(rule.getRuleName());
        dto.setRuleType(rule.getRuleType());
        dto.setConfig(rule.getConfig());
        dto.setEnabled(rule.getEnabled());
        dto.setVersion(rule.getVersion());
        dto.setAbCohort(rule.getAbCohort());
        dto.setCreatedAt(rule.getCreatedAt());
        dto.setUpdatedAt(rule.getUpdatedAt());
        dto.setUpdatedBy(rule.getUpdatedBy());
        dto.setRollbackFromRuleId(rule.getRollbackFromRuleId());
        return dto;
    }
}
