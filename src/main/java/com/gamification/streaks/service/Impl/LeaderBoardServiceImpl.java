package com.gamification.streaks.service.impl;

import com.gamification.streaks.dto.LeaderBoardEntryDto;
import com.gamification.streaks.execption.ResourceNotFoundException;
import com.gamification.streaks.model.LeaderBoardEntry;
import com.gamification.streaks.repository.LeaderBoardRepository;
import com.gamification.streaks.service.LeaderBoardService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final LeaderBoardRepository leaderBoardRepository;

    public LeaderBoardServiceImpl(LeaderBoardRepository leaderBoardRepository) {
        this.leaderBoardRepository = leaderBoardRepository;
    }

    public LeaderBoardEntry createLeaderBoard(LeaderBoardEntryDto leaderBoardEntryDto) {
        LeaderBoardEntry entry = new LeaderBoardEntry();
        entry.setLeaderboardId(UUID.randomUUID().toString());
        entry.setLeaderboardType(leaderBoardEntryDto.getLeaderboardType());
        entry.setUserId(leaderBoardEntryDto.getUserId());
        entry.setXpScore(leaderBoardEntryDto.getXpScore());
        entry.setRank(leaderBoardEntryDto.getRank());
        entry.setPeriodStart(leaderBoardEntryDto.getPeriodStart());
        entry.setPeriodEnd(leaderBoardEntryDto.getPeriodEnd());
        entry.setUpdatedAt(LocalDateTime.now());
        return leaderBoardRepository.save(entry);
    }

    public String createLeaderBoard(LeaderBoardEntry leaderBoardEntry) {
        leaderBoardEntry.setLeaderboardId(UUID.randomUUID().toString());
        leaderBoardRepository.save(leaderBoardEntry);
        return "LeaderBoard Entry Created Successfully";
    }

    public List<LeaderBoardEntryDto> getAllLeaderBoard() {
        List<LeaderBoardEntry> entries = leaderBoardRepository.findAll();
        List<LeaderBoardEntryDto> dtoList = new ArrayList<>();
        for (LeaderBoardEntry entry : entries) {
            dtoList.add(mapToDto(entry));
        }
        return dtoList;
    }

    public String updateLeaderBoard(String leaderboardId, LeaderBoardEntry leaderBoardEntry) {
        LeaderBoardEntry existing = leaderBoardRepository.findById(leaderboardId);
        if (existing == null) {
            throw new ResourceNotFoundException("LeaderBoard Entry Not Found");
        }
        existing.setLeaderboardType(leaderBoardEntry.getLeaderboardType());
        existing.setUserId(leaderBoardEntry.getUserId());
        existing.setXpScore(leaderBoardEntry.getXpScore());
        existing.setRank(leaderBoardEntry.getRank());
        existing.setPeriodStart(leaderBoardEntry.getPeriodStart());
        existing.setPeriodEnd(leaderBoardEntry.getPeriodEnd());
        existing.setUpdatedAt(LocalDateTime.now());
        leaderBoardRepository.save(existing);
        return "LeaderBoard Updated Successfully";
    }

    public String deleteLeaderBoard(String leaderboardId) {
        LeaderBoardEntry entry = leaderBoardRepository.findById(leaderboardId);
        if (entry == null) {
            throw new ResourceNotFoundException("LeaderBoard Entry Not Found");
        }
        leaderBoardRepository.delete(leaderboardId);
        return "LeaderBoard Deleted Successfully";
    }

    private LeaderBoardEntryDto mapToDto(LeaderBoardEntry entry) {
        LeaderBoardEntryDto dto = new LeaderBoardEntryDto();
        dto.setLeaderboardId(entry.getLeaderboardId());
        dto.setLeaderboardType(entry.getLeaderboardType());
        dto.setUserId(entry.getUserId());
        dto.setXpScore(entry.getXpScore());
        dto.setRank(entry.getRank());
        dto.setPeriodStart(entry.getPeriodStart());
        dto.setPeriodEnd(entry.getPeriodEnd());
        dto.setUpdatedAt(entry.getUpdatedAt());
        return dto;
    }
}
