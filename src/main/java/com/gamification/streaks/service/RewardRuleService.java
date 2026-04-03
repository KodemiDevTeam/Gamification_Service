package com.gamification.streaks.service;

import com.gamification.streaks.dto.RewardRuleDto;
import com.gamification.streaks.model.RewardRule;

import java.util.List;

public interface RewardRuleService {
    String createRewardRule(RewardRuleDto rewardRuleDto);
    String create(RewardRule rewardRule);
    List<RewardRuleDto> getAllRewardRule();
    String updateRewardRule(String ruleId,RewardRule rewardRule);
    String deleteRewardRule(String ruleId);
}
