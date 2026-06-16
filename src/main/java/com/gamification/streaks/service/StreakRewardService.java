package com.gamification.streaks.service;

import com.gamification.streaks.dto.StreakRewardDto;
import com.gamification.streaks.model.StreakReward;
import java.util.List;

public interface StreakRewardService {
    String createStreakReward(StreakRewardDto dto);
    String create(StreakReward streakReward);
    List<StreakRewardDto> getAllStreakRewards();
    StreakRewardDto getStreakRewardById(String id);
    String updateStreakReward(String id, StreakReward streakReward);
    String deleteStreakReward(String id);
}
