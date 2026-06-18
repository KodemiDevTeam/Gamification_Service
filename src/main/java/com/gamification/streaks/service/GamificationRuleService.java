package com.gamification.streaks.service;

import com.gamification.streaks.dto.GamificationRuleDto;
import com.gamification.streaks.model.GamificationRule;

import java.util.List;

public interface GamificationRuleService {
    String createGamificationRule(GamificationRuleDto gamificationRuleDto);
    String create(GamificationRule gamificationRule);
    List<GamificationRuleDto> getAllRules();
    List<GamificationRuleDto> update(String ruleId, GamificationRule gamificationRule);
    String deleteGamificationRule(String ruleId);
    GamificationRuleDto toggleRule(String ruleId);
    GamificationRuleDto getCoinConversionRule();
}

