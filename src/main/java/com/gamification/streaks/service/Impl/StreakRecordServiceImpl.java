package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.StreakRecordDto;
import com.gamification.streaks.model.StreakRecord;
import com.gamification.streaks.repository.StreakRecordRepository;
import com.gamification.streaks.service.StreakRecordService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class StreakRecordServiceImpl implements StreakRecordService {
    private final StreakRecordRepository streakRecordRepository;
    public StreakRecordServiceImpl(StreakRecordRepository streakRecordRepository){
        this.streakRecordRepository = streakRecordRepository;
    }
    public String createStreakRecord(StreakRecordDto dto){
        StreakRecord record = new StreakRecord();
        record.setStreakId(UUID.randomUUID().toString());
        record.setUserId(dto.getUserId());
        record.setStreakType(dto.getStreakType());
        record.setCurrentStreakCount(dto.getCurrentStreakCount());
        record.setLongestStreakCount(dto.getLongestStreakCount());
        record.setLastActiveData(dto.getLastActiveData());
        record.setStreakStatus(dto.getStreakStatus());
        record.setCreatedAt(dto.getCreatedAt());
        record.setUpdatedAt(dto.getUpdatedAt());
        streakRecordRepository.save(record);
        return "Streak Record Created Successfully";
    }
    public String create(StreakRecord streakRecord){
        streakRecord.setStreakId(UUID.randomUUID().toString());
        streakRecordRepository.save(streakRecord);
        return "Streak Record Created Successfully";
    }

    public List<StreakRecordDto> getAllStreakRecord(){
        List<StreakRecord> records = streakRecordRepository.findAll();
        List<StreakRecordDto> dtoList = new ArrayList<>();
        for(StreakRecord record : records){
            dtoList.add(mapToDto(record));
        }

        return dtoList;
    }
    public String updateStreak(String streakId, StreakRecord streakRecord){
        StreakRecord existing = streakRecordRepository.findById(streakId);
        if(existing == null){
            throw new RuntimeException("Streak Record Not Found");
        }

        existing.setUserId(streakRecord.getUserId());
        existing.setStreakType(streakRecord.getStreakType());
        existing.setCurrentStreakCount(streakRecord.getCurrentStreakCount());
        existing.setLongestStreakCount(streakRecord.getLongestStreakCount());
        existing.setLastActiveData(streakRecord.getLastActiveData());
        existing.setStreakStatus(streakRecord.getStreakStatus());
        existing.setUpdatedAt(streakRecord.getUpdatedAt());
        streakRecordRepository.save(existing);
        return "Streak Record Updated Successfully";
    }
    public String deleteStreak(String streakId){
        StreakRecord record = streakRecordRepository.findById(streakId);
        if(record == null){
            throw new RuntimeException("Streak Record Not Found");
        }
        streakRecordRepository.delete(streakId);

        return "Streak Record Deleted Successfully";
    }

    private StreakRecordDto mapToDto(StreakRecord record){
        StreakRecordDto dto = new StreakRecordDto();
        dto.setStreakId(record.getStreakId());
        dto.setUserId(record.getUserId());
        dto.setStreakType(record.getStreakType());
        dto.setCurrentStreakCount(record.getCurrentStreakCount());
        dto.setLongestStreakCount(record.getLongestStreakCount());
        dto.setLastActiveData(record.getLastActiveData());
        dto.setStreakStatus(record.getStreakStatus());
        dto.setCreatedAt(record.getCreatedAt());
        dto.setUpdatedAt(record.getUpdatedAt());

        return dto;
    }
}