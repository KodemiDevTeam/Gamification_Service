package com.gamification.streaks.service.impl;

import com.gamification.streaks.dto.StreakRecordDto;
import com.gamification.streaks.execption.ResourceNotFoundException;
import com.gamification.streaks.model.StreakRecord;
import com.gamification.streaks.repository.StreakRecordRepository;
import com.gamification.streaks.service.StreakRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StreakRecordServiceImpl implements StreakRecordService {

    private final StreakRecordRepository streakRecordRepository;

    public StreakRecordServiceImpl(StreakRecordRepository streakRecordRepository) {
        this.streakRecordRepository = streakRecordRepository;
    }

    public String createStreakRecord(StreakRecordDto dto) {
        StreakRecord streakRecord = new StreakRecord();
        streakRecord.setStreakId(UUID.randomUUID().toString());
        streakRecord.setUserId(dto.getUserId());
        streakRecord.setStreakType(dto.getStreakType());
        streakRecord.setCurrentStreakCount(dto.getCurrentStreakCount());
        streakRecord.setLongestStreakCount(dto.getLongestStreakCount());
        streakRecord.setLastActiveData(dto.getLastActiveData());
        streakRecord.setStreakStatus(dto.getStreakStatus());
        streakRecord.setCreatedAt(LocalDateTime.now());
        streakRecord.setUpdatedAt(LocalDateTime.now());
        streakRecordRepository.save(streakRecord);
        return "Streak Record Created Successfully";
    }

    public String create(StreakRecord streakRecord) {
        streakRecord.setStreakId(UUID.randomUUID().toString());
        streakRecordRepository.save(streakRecord);
        return "Streak Record Created Successfully";
    }

    public List<StreakRecordDto> getAllStreakRecord() {
        List<StreakRecord> streakRecords = streakRecordRepository.findAll();
        List<StreakRecordDto> dtoList = new ArrayList<>();
        for (StreakRecord streakRecord : streakRecords) {
            dtoList.add(mapToDto(streakRecord));
        }
        return dtoList;
    }

    public String updateStreak(String streakId, StreakRecord streakRecord) {
        StreakRecord existing = streakRecordRepository.findById(streakId);
        if (existing == null) {
            throw new ResourceNotFoundException("Streak Record Not Found");
        }
        existing.setUserId(streakRecord.getUserId());
        existing.setStreakType(streakRecord.getStreakType());
        existing.setCurrentStreakCount(streakRecord.getCurrentStreakCount());
        existing.setLongestStreakCount(streakRecord.getLongestStreakCount());
        existing.setLastActiveData(streakRecord.getLastActiveData());
        existing.setStreakStatus(streakRecord.getStreakStatus());
        existing.setUpdatedAt(LocalDateTime.now());
        streakRecordRepository.save(existing);
        return "Streak Record Updated Successfully";
    }

    public String deleteStreak(String streakId) {
        StreakRecord streakRecord = streakRecordRepository.findById(streakId);
        if (streakRecord == null) {
            throw new ResourceNotFoundException("Streak Record Not Found");
        }
        streakRecordRepository.delete(streakId);
        return "Streak Record Deleted Successfully";
    }

    private StreakRecordDto mapToDto(StreakRecord streakRecord) {
        StreakRecordDto dto = new StreakRecordDto();
        dto.setStreakId(streakRecord.getStreakId());
        dto.setUserId(streakRecord.getUserId());
        dto.setStreakType(streakRecord.getStreakType());
        dto.setCurrentStreakCount(streakRecord.getCurrentStreakCount());
        dto.setLongestStreakCount(streakRecord.getLongestStreakCount());
        dto.setLastActiveData(streakRecord.getLastActiveData());
        dto.setStreakStatus(streakRecord.getStreakStatus());
        dto.setCreatedAt(streakRecord.getCreatedAt());
        dto.setUpdatedAt(streakRecord.getUpdatedAt());
        return dto;
    }
}
