package com.gamification.streaks.controllers;

import com.gamification.streaks.dto.LeaderBoardEntryDto;
import com.gamification.streaks.model.LeaderBoardEntry;
import com.gamification.streaks.service.LeaderBoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    public LeaderBoardController(LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @PostMapping("/create-dto")
    public LeaderBoardEntry createLeaderBoard(@RequestBody LeaderBoardEntryDto leaderBoardEntryDto) {
        return leaderBoardService.createLeaderBoard(leaderBoardEntryDto);
    }

    @PostMapping("/create")
    public String createLeaderBoard(@RequestBody LeaderBoardEntry leaderBoardEntry) {
        return leaderBoardService.createLeaderBoard(leaderBoardEntry);
    }

    @GetMapping("/all")
    public List<LeaderBoardEntryDto> getAllLeaderBoard() {
        return leaderBoardService.getAllLeaderBoard();
    }

    @PutMapping("/{leaderboardId}")
    public String updateLeaderBoard(@PathVariable String leaderboardId, @RequestBody LeaderBoardEntry leaderBoardEntry) {
        return leaderBoardService.updateLeaderBoard(leaderboardId, leaderBoardEntry);
    }

    @DeleteMapping("/{leaderboardId}")
    public String deleteLeaderBoard(@PathVariable String leaderboardId) {
        return leaderBoardService.deleteLeaderBoard(leaderboardId);
    }
}
