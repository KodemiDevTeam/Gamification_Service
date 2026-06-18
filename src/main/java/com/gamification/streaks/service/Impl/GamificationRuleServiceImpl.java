package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.GamificationRuleDto;
import com.gamification.streaks.enums.RuleType;
import com.gamification.streaks.model.GamificationRule;
import com.gamification.streaks.repository.GamificationRuleRepository;
import com.gamification.streaks.service.GamificationRuleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GamificationRuleServiceImpl implements GamificationRuleService {

    private final GamificationRuleRepository gamificationRuleRepository;

    public GamificationRuleServiceImpl(GamificationRuleRepository gamificationRuleRepository){
        this.gamificationRuleRepository = gamificationRuleRepository;
    }

    public String createGamificationRule(GamificationRuleDto dto){
        GamificationRule rule = new GamificationRule();
        rule.setRuleId(UUID.randomUUID().toString());
        rule.setRuleName(dto.getRuleName());
        rule.setRuleType(dto.getRuleType());
        rule.setConfig(dto.getConfig());
        rule.setConditionExpression(dto.getConditionExpression());
        rule.setEnabled(dto.getEnabled());
        rule.setVersion(dto.getVersion());
        rule.setAbCohort(dto.getAbCohort());
        rule.setCreatedAt(dto.getCreatedAt());
        rule.setUpdatedAt(dto.getUpdatedAt());
        rule.setUpdatedBy(dto.getUpdatedBy());
        rule.setRollbackFromRuleId(dto.getRollbackFromRuleId());
        gamificationRuleRepository.save(rule);
        return "Gamification Rule Created Successfully";
    }

    public String create(GamificationRule gamificationRule){
        gamificationRule.setRuleId(UUID.randomUUID().toString());
        gamificationRuleRepository.save(gamificationRule);
        return "Gamification Rule Created Successfully";
    }

    public List<GamificationRuleDto> getAllRules(){
        List<GamificationRule> rules = gamificationRuleRepository.findAll();
        List<GamificationRuleDto> dtoList = new ArrayList<>();
        for(GamificationRule rule : rules){
            dtoList.add(mapToDto(rule));
        }
        return dtoList;
    }

    public List<GamificationRuleDto> update(String ruleId, GamificationRule gamificationRule){
        GamificationRule existing = gamificationRuleRepository.findById(ruleId);
        if(existing == null){
            throw new RuntimeException("Gamification Rule Not Found");
        }
        existing.setRuleName(gamificationRule.getRuleName());
        existing.setRuleType(gamificationRule.getRuleType());
        existing.setConfig(gamificationRule.getConfig());
        existing.setConditionExpression(gamificationRule.getConditionExpression());
        existing.setEnabled(gamificationRule.getEnabled());
        existing.setVersion(gamificationRule.getVersion());
        existing.setAbCohort(gamificationRule.getAbCohort());
        existing.setUpdatedAt(LocalDateTime.now().toString());
        existing.setUpdatedBy(gamificationRule.getUpdatedBy());
        existing.setRollbackFromRuleId(gamificationRule.getRollbackFromRuleId());
        gamificationRuleRepository.save(existing);

        return getAllRules();
    }

    public String deleteGamificationRule(String ruleId){
        GamificationRule rule = gamificationRuleRepository.findById(ruleId);
        if(rule == null){
            throw new RuntimeException("Gamification Rule Not Found");
        }
        gamificationRuleRepository.delete(ruleId);
        return "Gamification Rule Deleted Successfully";
    }

    /**
     * Flips the enabled flag on a rule and persists it.
     * Used by the toggle switch in the admin UI Reward Rules list.
     */
    public GamificationRuleDto toggleRule(String ruleId){
        GamificationRule rule = gamificationRuleRepository.findById(ruleId);
        if(rule == null){
            throw new RuntimeException("Gamification Rule Not Found");
        }
        rule.setEnabled(!Boolean.TRUE.equals(rule.getEnabled()));
        rule.setUpdatedAt(LocalDateTime.now().toString());
        gamificationRuleRepository.save(rule);
        return mapToDto(rule);
    }

    /**
     * Returns the COIN_CONVERSION rule — used to render the XP→Coin banner in the admin UI.
     */
    public GamificationRuleDto getCoinConversionRule(){
        List<GamificationRule> rules = gamificationRuleRepository.findAll();
        for(GamificationRule rule : rules){
            if(RuleType.COIN_CONVERSION.name().equals(rule.getRuleType())){
                return mapToDto(rule);
            }
        }
        throw new RuntimeException("Coin Conversion Rule not configured");
    }

    private GamificationRuleDto mapToDto(GamificationRule rule){
        GamificationRuleDto dto = new GamificationRuleDto();
        dto.setRuleId(rule.getRuleId());
        dto.setRuleName(rule.getRuleName());
        dto.setRuleType(rule.getRuleType());
        dto.setConfig(rule.getConfig());
        dto.setConditionExpression(rule.getConditionExpression());
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