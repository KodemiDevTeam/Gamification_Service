package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.RewardRuleDto;
import com.gamification.streaks.model.RewardRule;
import com.gamification.streaks.service.RewardRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rule")
public class RewardRuleController {

    private final RewardRuleService rewardRuleService;

    public RewardRuleController(RewardRuleService rewardRuleService) {
        this.rewardRuleService = rewardRuleService;
    }

    @PostMapping("/create-dto")
    public String createRewardRule(@RequestBody RewardRuleDto rewardRuleDto) {
        return rewardRuleService.createRewardRule(rewardRuleDto);
    }

    @PostMapping("/create")
    public String create(@RequestBody RewardRule rewardRule) {
        return rewardRuleService.create(rewardRule);
    }

    @GetMapping("/all")
    public List<RewardRuleDto> getAllRewardRules() {
        return rewardRuleService.getAllRewardRule();
    }

    @PutMapping("/{ruleId}")
    public String updateRewardRule(@PathVariable String ruleId, @RequestBody RewardRule rewardRule) {
        return rewardRuleService.updateRewardRule(ruleId, rewardRule);
    }

    @DeleteMapping("/{ruleId}")
    public String deleteRewardRule(@PathVariable String ruleId) {
        return rewardRuleService.deleteRewardRule(ruleId);
    }
}
