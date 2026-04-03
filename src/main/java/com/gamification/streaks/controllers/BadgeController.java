package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.BadgeDto;
import com.gamification.streaks.model.Badge;
import com.gamification.streaks.service.BadgeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badge")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping("/create-dto")
    public Badge createBadge(@RequestBody BadgeDto badgeDto) {
        return badgeService.createBadge(badgeDto);
    }

    @PostMapping("/create")
    public String createBadge(@RequestBody Badge badge) {
        return badgeService.createBade(badge);
    }

    @GetMapping("/{badgeId}")
    public BadgeDto getBadgeById(@PathVariable String badgeId) {
        return badgeService.getBadgeId(badgeId);
    }

    @GetMapping("/all")
    public List<BadgeDto> getAllBadges() {
        return badgeService.getAllBadge();
    }

    @PutMapping("/{badgeId}")
    public String updateBadge(@PathVariable String badgeId, @RequestBody Badge badge) {
        return badgeService.updateBadge(badgeId, badge);
    }

    @DeleteMapping("/{badgeId}")
    public String deleteBadge(@PathVariable String badgeId) {
        return badgeService.deleteBadge(badgeId);
    }
}
