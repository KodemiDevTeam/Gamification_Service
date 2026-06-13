package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.GamificationRuleDto;
import com.gamification.streaks.model.GamificationRule;
import com.gamification.streaks.service.GamificationRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamification-rule")
public class GamificationRuleController {

    private final GamificationRuleService gamificationRuleService;

    public GamificationRuleController(GamificationRuleService gamificationRuleService) {
        this.gamificationRuleService = gamificationRuleService;
    }

    @PostMapping("/create-dto")
    public String createGamificationRule(@RequestBody GamificationRuleDto gamificationRuleDto) {
        return gamificationRuleService.createGamificationRule(gamificationRuleDto);
    }

    @PostMapping("/create")
    public String create(@RequestBody GamificationRule gamificationRule) {
        return gamificationRuleService.create(gamificationRule);
    }

    @PutMapping("/{ruleId}")
    public List<GamificationRuleDto> updateGamificationRule(@PathVariable String ruleId, @RequestBody GamificationRule gamificationRule) {
        return gamificationRuleService.update(ruleId, gamificationRule);
    }

    @DeleteMapping("/{ruleId}")
    public String deleteGamificationRule(@PathVariable String ruleId) {
        return gamificationRuleService.deleteGamificationRule(ruleId);
    }
}
