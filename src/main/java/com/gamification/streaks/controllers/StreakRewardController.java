package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.StreakRewardDto;
import com.gamification.streaks.model.StreakReward;
import com.gamification.streaks.service.StreakRewardService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/streak-rewards")
public class StreakRewardController {

    private final StreakRewardService streakRewardService;

    public StreakRewardController(StreakRewardService streakRewardService) {
        this.streakRewardService = streakRewardService;
    }

    @PostMapping("/create-dto")
    public String createStreakReward(@RequestBody StreakRewardDto dto) {
        return streakRewardService.createStreakReward(dto);
    }

    @PostMapping("/create")
    public String create(@RequestBody StreakReward streakReward) {
        return streakRewardService.create(streakReward);
    }

    @GetMapping("/all")
    public List<StreakRewardDto> getAllStreakRewards() {
        return streakRewardService.getAllStreakRewards();
    }

    @GetMapping("/{id}")
    public StreakRewardDto getStreakRewardById(@PathVariable String id) {
        return streakRewardService.getStreakRewardById(id);
    }

    @PutMapping("/{id}")
    public String updateStreakReward(@PathVariable String id, @RequestBody StreakReward streakReward) {
        return streakRewardService.updateStreakReward(id, streakReward);
    }

    @DeleteMapping("/{id}")
    public String deleteStreakReward(@PathVariable String id) {
        return streakRewardService.deleteStreakReward(id);
    }
}
