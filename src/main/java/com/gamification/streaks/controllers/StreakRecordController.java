package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.StreakRecordDto;
import com.gamification.streaks.model.StreakRecord;
import com.gamification.streaks.service.StreakRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streak-record")
public class StreakRecordController {

    private final StreakRecordService streakRecordService;

    public StreakRecordController(StreakRecordService streakRecordService) {
        this.streakRecordService = streakRecordService;
    }

    @PostMapping("/create-dto")
    public String createStreakRecord(@RequestBody StreakRecordDto streakRecordDto) {
        return streakRecordService.createStreakRecord(streakRecordDto);
    }

    @PostMapping("/create")
    public String create(@RequestBody StreakRecord streakRecord) {
        return streakRecordService.create(streakRecord);
    }

    @GetMapping("/all")
    public List<StreakRecordDto> getAllStreakRecords() {
        return streakRecordService.getAllStreakRecord();
    }

    @PutMapping("/{streakId}")
    public String updateStreak(@PathVariable String streakId, @RequestBody StreakRecord streakRecord) {
        return streakRecordService.updateStreak(streakId, streakRecord);
    }

    @DeleteMapping("/{streakId}")
    public String deleteStreak(@PathVariable String streakId) {
        return streakRecordService.deleteStreak(streakId);
    }
}
