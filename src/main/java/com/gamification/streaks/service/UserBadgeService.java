package com.gamification.streaks.service;

import com.gamification.streaks.dto.UserBadgeDto;
import com.gamification.streaks.model.UserBadge;

import java.util.List;

public interface UserBadgeService {
    String createUserBadge(UserBadgeDto userBadgeDto);
    String create(UserBadge userBadge);
    List<UserBadgeDto> getAllUserBadge();
    String updateUserBadge(String userBadgeId,UserBadge userBadge);
    String deleteUserBadge(String userBadgeId);
}
