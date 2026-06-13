package com.gamification.streaks.service;

import com.gamification.streaks.dto.BadgeDto;
import com.gamification.streaks.model.Badge;

import java.util.List;

public interface BadgeService{
    Badge createBadge(BadgeDto badgeDto);
    String createBade(Badge badge);
    BadgeDto getBadgeId(String badgeId);
    List<BadgeDto> getAllBadge();
    String updateBadge(String badgeId, Badge badge);
    String deleteBadge(String badgeId);
}
