package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.UserChallengePerformanceDto;
import com.gamification.streaks.model.UserChallengePerformance;
import com.gamification.streaks.service.UserChallengePerformanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-performance")
public class UserChallengePerformanceController {

    private final UserChallengePerformanceService performanceService;

    public UserChallengePerformanceController(UserChallengePerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @PostMapping("/submit")
    public UserChallengePerformance submitPerformance(@RequestBody UserChallengePerformanceDto dto) {
        return performanceService.submitPerformance(dto);
    }

    @GetMapping("/{performanceId}")
    public UserChallengePerformance getPerformanceById(@PathVariable String performanceId) {
        return performanceService.getPerformanceById(performanceId);
    }

    @GetMapping("/user/{userId}")
    public List<UserChallengePerformance> getPerformancesByUserId(@PathVariable String userId) {
        return performanceService.getPerformancesByUserId(userId);
    }

    @GetMapping("/challenge/{challengeId}")
    public List<UserChallengePerformance> getPerformancesByChallengeId(@PathVariable String challengeId) {
        return performanceService.getPerformancesByChallengeId(challengeId);
    }

    @GetMapping("/all")
    public List<UserChallengePerformance> getAllPerformances() {
        return performanceService.getAllPerformances();
    }
}
