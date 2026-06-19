package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.ChallengeRuleDto;
import com.gamification.streaks.model.ChallengeRule;
import com.gamification.streaks.service.ChallengeRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge-rules")
public class ChallengeRuleController {

    private final ChallengeRuleService challengeRuleService;

    public ChallengeRuleController(ChallengeRuleService challengeRuleService) {
        this.challengeRuleService = challengeRuleService;
    }

    @PostMapping("/create")
    public ChallengeRule createRule(@RequestBody ChallengeRuleDto dto) {
        return challengeRuleService.createRule(dto);
    }

    @GetMapping("/{ruleId}")
    public ChallengeRule getRuleById(@PathVariable String ruleId) {
        return challengeRuleService.getRuleById(ruleId);
    }

    @GetMapping("/challenge/{challengeId}")
    public List<ChallengeRule> getRulesByChallengeId(@PathVariable String challengeId) {
        return challengeRuleService.getRulesByChallengeId(challengeId);
    }

    @GetMapping("/all")
    public List<ChallengeRule> getAllRules() {
        return challengeRuleService.getAllRules();
    }

    @PutMapping("/{ruleId}")
    public ChallengeRule updateRule(@PathVariable String ruleId, @RequestBody ChallengeRule rule) {
        return challengeRuleService.updateRule(ruleId, rule);
    }

    @DeleteMapping("/{ruleId}")
    public String deleteRule(@PathVariable String ruleId) {
        challengeRuleService.deleteRule(ruleId);
        return "Challenge Rule Deleted Successfully";
    }
}
