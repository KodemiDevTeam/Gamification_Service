package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.UserBadgeDto;
import com.gamification.streaks.model.UserBadge;
import com.gamification.streaks.service.UserBadgeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-badge")
public class UserBadgeController {

    private final UserBadgeService userBadgeService;

    public UserBadgeController(UserBadgeService userBadgeService) {
        this.userBadgeService = userBadgeService;
    }

    @PostMapping("/create-dto")
    public String createUserBadge(@RequestBody UserBadgeDto userBadgeDto) {
        return userBadgeService.createUserBadge(userBadgeDto);
    }

    @PostMapping("/create")
    public String create(@RequestBody UserBadge userBadge) {
        return userBadgeService.create(userBadge);
    }

    @GetMapping("/all")
    public List<UserBadgeDto> getAllUserBadges() {
        return userBadgeService.getAllUserBadge();
    }

    @PutMapping("/{userBadgeId}")
    public String updateUserBadge(@PathVariable String userBadgeId, @RequestBody UserBadge userBadge) {
        return userBadgeService.updateUserBadge(userBadgeId, userBadge);
    }

    @DeleteMapping("/{userBadgeId}")
    public String deleteUserBadge(@PathVariable String userBadgeId) {
        return userBadgeService.deleteUserBadge(userBadgeId);
    }

    @GetMapping("/user/{userId}")
    public List<UserBadgeDto> getUserBadgesByUserId(@PathVariable String userId) {
        return userBadgeService.getUserBadgesByUserId(userId);
    }
}
