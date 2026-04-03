package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.UserGamificationProfileDto;
import com.gamification.streaks.model.UserGamificationProfile;
import com.gamification.streaks.service.UserGamificationProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-gamification")
public class UserGamificationProfileController {

    private final UserGamificationProfileService userGamificationProfileService;

    public UserGamificationProfileController(UserGamificationProfileService userGamificationProfileService){
        this.userGamificationProfileService = userGamificationProfileService;
    }

    @PostMapping("/create-dto")
    public UserGamificationProfile createUserGamification(@RequestBody UserGamificationProfileDto dto){
        return userGamificationProfileService.createUserGamification(dto);
    }

    @PostMapping("/create")
    public String createUserGamificationProfile(@RequestBody UserGamificationProfile profile){
        return userGamificationProfileService.createUserGamificationProfile(profile);
    }

    @GetMapping("/{userId}")
    public UserGamificationProfileDto getUserGamificationProfile(@PathVariable String userId){
        return userGamificationProfileService.getUserGamificationProfileById(userId);
    }

    @GetMapping("/all")
    public List<UserGamificationProfileDto> getAllUserGamificationProfiles(){
        return userGamificationProfileService.getAllUserGamificationProfiles();
    }

    @PutMapping("/{userId}")
    public String updateUserGamificationProfile(
            @PathVariable String userId,
            @RequestBody UserGamificationProfile profile){
        return userGamificationProfileService.updateUserGamificationProfile(userId, profile);
    }

    @DeleteMapping("/{userId}")
    public String deleteUserGamificationProfile(@PathVariable String userId){
        return userGamificationProfileService.deleteUserGamificationProfile(userId);
    }
}