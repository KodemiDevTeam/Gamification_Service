package com.gamification.streaks.service.Impl;

import com.gamification.streaks.dto.ChallengeRuleDto;
import com.gamification.streaks.model.ChallengeRule;
import com.gamification.streaks.repository.ChallengeRuleRepository;
import com.gamification.streaks.service.ChallengeRuleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChallengeRuleServiceImpl implements ChallengeRuleService {

    private final ChallengeRuleRepository challengeRuleRepository;

    public ChallengeRuleServiceImpl(ChallengeRuleRepository challengeRuleRepository) {
        this.challengeRuleRepository = challengeRuleRepository;
    }

    @Override
    public ChallengeRule createRule(ChallengeRuleDto dto) {
        ChallengeRule rule = new ChallengeRule();
        rule.setRuleId(UUID.randomUUID().toString());
        rule.setChallengeId(dto.getChallengeId());
        rule.setTotalMarks(dto.getTotalMarks());
        rule.setPassMark(dto.getPassMark());
        rule.setRewardCoins(dto.getRewardCoins());
        rule.setRewardXp(dto.getRewardXp());
        rule.setActive(dto.getActive() != null ? dto.getActive() : true);
        rule.setCreatedAt(LocalDateTime.now().toString());
        rule.setUpdatedAt(LocalDateTime.now().toString());
        return challengeRuleRepository.save(rule);
    }

    @Override
    public ChallengeRule getRuleById(String ruleId) {
        ChallengeRule rule = challengeRuleRepository.findById(ruleId);
        if (rule == null) {
            throw new RuntimeException("Challenge Rule not found");
        }
        return rule;
    }

    @Override
    public List<ChallengeRule> getRulesByChallengeId(String challengeId) {
        return challengeRuleRepository.findByChallengeId(challengeId);
    }

    @Override
    public List<ChallengeRule> getAllRules() {
        return challengeRuleRepository.findAll();
    }

    @Override
    public ChallengeRule updateRule(String ruleId, ChallengeRule rule) {
        ChallengeRule existing = challengeRuleRepository.findById(ruleId);
        if (existing == null) {
            throw new RuntimeException("Challenge Rule not found");
        }
        existing.setTotalMarks(rule.getTotalMarks());
        existing.setPassMark(rule.getPassMark());
        existing.setRewardCoins(rule.getRewardCoins());
        existing.setRewardXp(rule.getRewardXp());
        existing.setActive(rule.getActive());
        existing.setUpdatedAt(LocalDateTime.now().toString());
        return challengeRuleRepository.save(existing);
    }

    @Override
    public void deleteRule(String ruleId) {
        ChallengeRule existing = challengeRuleRepository.findById(ruleId);
        if (existing == null) {
            throw new RuntimeException("Challenge Rule not found");
        }
        challengeRuleRepository.delete(ruleId);
    }
}
