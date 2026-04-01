package com.gamification.streaks.service;

import com.gamification.streaks.dto.UserGamificationProfileDto;
import com.gamification.streaks.model.UserGamificationProfile;

import java.util.List;

public interface UserGamificationProfileService {
    UserGamificationProfile createUserGamification(UserGamificationProfileDto dto);

    String createUserGamificationProfile(UserGamificationProfile userGamificationProfile);
    UserGamificationProfileDto getUserGamificationProfileById(String userId);
    List<UserGamificationProfileDto> getAllUserGamificationProfiles();
    String updateUserGamificationProfile(String userId, UserGamificationProfile userGamificationProfile);
    String deleteUserGamificationProfile(String userId);
}