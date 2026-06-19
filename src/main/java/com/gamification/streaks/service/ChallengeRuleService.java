package com.gamification.streaks.service;

import com.gamification.streaks.dto.ChallengeRuleDto;
import com.gamification.streaks.model.ChallengeRule;

import java.util.List;

public interface ChallengeRuleService {
    ChallengeRule createRule(ChallengeRuleDto dto);
    ChallengeRule getRuleById(String ruleId);
    List<ChallengeRule> getRulesByChallengeId(String challengeId);
    List<ChallengeRule> getAllRules();
    ChallengeRule updateRule(String ruleId, ChallengeRule rule);
    void deleteRule(String ruleId);
}
