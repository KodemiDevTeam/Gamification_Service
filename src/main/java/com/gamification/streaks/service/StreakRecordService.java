package com.gamification.streaks.service;

import com.gamification.streaks.dto.StreakRecordDto;
import com.gamification.streaks.model.StreakRecord;

import java.util.List;

public interface StreakRecordService {

    String createStreakRecord(StreakRecordDto streakRecordDto);

    String create(StreakRecord streakRecord);

    List<StreakRecordDto> getAllStreakRecord();

    String updateStreak(String streakId, StreakRecord streakRecord);

    String deleteStreak(String streakId);
}