package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.UserChallengeDto;
import com.gamification.streaks.service.UserChallengeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-challenge")
public class UserChallengeController {

    private final UserChallengeService userChallengeService;

    public UserChallengeController(UserChallengeService userChallengeService) {
        this.userChallengeService = userChallengeService;
    }

    @PostMapping("/enroll")
    public UserChallengeDto enrollUser(
            @RequestParam String userId,
            @RequestParam String challengeId) {
        return userChallengeService.enrollUser(userId, challengeId);
    }

    @PostMapping("/progress")
    public UserChallengeDto updateProgress(
            @RequestParam String userId,
            @RequestParam String challengeId,
            @RequestParam Integer progress) {
        return userChallengeService.updateProgress(userId, challengeId, progress);
    }

    @GetMapping("/{userId}")
    public List<UserChallengeDto> getUserChallenges(@PathVariable String userId) {
        return userChallengeService.getUserChallenges(userId);
    }

    @GetMapping("/{userId}/{challengeId}")
    public UserChallengeDto getUserChallenge(
            @PathVariable String userId,
            @PathVariable String challengeId) {
        return userChallengeService.getUserChallenge(userId, challengeId);
    }
}
