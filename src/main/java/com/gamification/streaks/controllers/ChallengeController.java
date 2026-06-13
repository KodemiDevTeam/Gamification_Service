package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.ChallengeDto;
import com.gamification.streaks.model.Challenge;
import com.gamification.streaks.service.ChallengeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/create-dto")
    public String createChallenge(@RequestBody ChallengeDto challengeDto) {
        return challengeService.createChallenge(challengeDto);
    }

    @PostMapping("/create")
    public String create(@RequestBody Challenge challenge) {
        return challengeService.create(challenge);
    }

    @GetMapping("/all")
    public List<ChallengeDto> getAllChallenges() {
        return challengeService.getAllChallenge();
    }

    @PutMapping("/{challengeId}")
    public String updateChallenge(@PathVariable String challengeId, @RequestBody Challenge challenge) {
        return challengeService.updateChallenge(challengeId, challenge);
    }

    @DeleteMapping("/{challengeId}")
    public String deleteChallenge(@PathVariable String challengeId) {
        return challengeService.deleteChallenge(challengeId);
    }
}
