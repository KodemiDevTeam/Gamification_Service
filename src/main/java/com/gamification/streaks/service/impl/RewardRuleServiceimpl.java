package com.gamification.streaks.service.impl;

import com.gamification.streaks.dto.RewardRuleDto;
import com.gamification.streaks.execption.ResourceNotFoundException;
import com.gamification.streaks.model.RewardRule;
import com.gamification.streaks.repository.RewardRuleRepository;
import com.gamification.streaks.service.RewardRuleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RewardRuleServiceimpl implements RewardRuleService {

    private final RewardRuleRepository rewardRuleRepository;

    public RewardRuleServiceimpl(RewardRuleRepository rewardRuleRepository) {
        this.rewardRuleRepository = rewardRuleRepository;
    }

    public String createRewardRule(RewardRuleDto rewardRuleDto) {
        RewardRule rule = new RewardRule();
        rule.setRuleId(UUID.randomUUID().toString());
        rule.setRuleName(rewardRuleDto.getRuleName());
        rule.setRuleType(rewardRuleDto.getRuleType());
        rule.setConditionType(rewardRuleDto.getConditionType());
        rule.setConditionValue(rewardRuleDto.getConditionValue());
        rule.setRewardType(rewardRuleDto.getRewardType());
        rule.setRewardValue(rewardRuleDto.getRewardValue());
        rule.setActive(rewardRuleDto.getActive());
        rule.setCreatedAt(LocalDateTime.now());
        rewardRuleRepository.save(rule);
        return "Reward Rule Created Successfully";
    }

    public String create(RewardRule rewardRule) {
        rewardRule.setRuleId(UUID.randomUUID().toString());
        rewardRuleRepository.save(rewardRule);
        return "Reward Rule Created Successfully";
    }

    public List<RewardRuleDto> getAllRewardRule() {
        List<RewardRule> rules = rewardRuleRepository.findAll();
        List<RewardRuleDto> dtoList = new ArrayList<>();
        for (RewardRule rule : rules) {
            dtoList.add(mapToDto(rule));
        }
        return dtoList;
    }

    public String updateRewardRule(String ruleId, RewardRule rewardRule) {
        RewardRule existing = rewardRuleRepository.findById(ruleId);
        if (existing == null) {
            throw new ResourceNotFoundException("Reward Rule Not Found");
        }
        existing.setRuleName(rewardRule.getRuleName());
        existing.setRuleType(rewardRule.getRuleType());
        existing.setConditionType(rewardRule.getConditionType());
        existing.setConditionValue(rewardRule.getConditionValue());
        existing.setRewardType(rewardRule.getRewardType());
        existing.setRewardValue(rewardRule.getRewardValue());
        existing.setActive(rewardRule.getActive());
        rewardRuleRepository.save(existing);
        return "Reward Rule Updated Successfully";
    }

    public String deleteRewardRule(String ruleId) {
        RewardRule rule = rewardRuleRepository.findById(ruleId);
        if (rule == null) {
            throw new ResourceNotFoundException("Reward Rule Not Found");
        }
        rewardRuleRepository.delete(ruleId);
        return "Reward Rule Deleted Successfully";
    }

    private RewardRuleDto mapToDto(RewardRule rule) {
        RewardRuleDto dto = new RewardRuleDto();
        dto.setRuleId(rule.getRuleId());
        dto.setRuleName(rule.getRuleName());
        dto.setRuleType(rule.getRuleType());
        dto.setConditionType(rule.getConditionType());
        dto.setConditionValue(rule.getConditionValue());
        dto.setRewardType(rule.getRewardType());
        dto.setRewardValue(rule.getRewardValue());
        dto.setActive(rule.getActive());
        dto.setCreatedAt(rule.getCreatedAt());
        return dto;
    }
}
