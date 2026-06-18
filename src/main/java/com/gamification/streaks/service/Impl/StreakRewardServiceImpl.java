package com.gamification.streaks.service.Impl;

import com.gamification.streaks.dto.StreakRewardDto;
import com.gamification.streaks.model.StreakReward;
import com.gamification.streaks.repository.StreakRewardRepository;
import com.gamification.streaks.service.StreakRewardService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StreakRewardServiceImpl implements StreakRewardService {

    private final StreakRewardRepository streakRewardRepository;

    public StreakRewardServiceImpl(StreakRewardRepository streakRewardRepository) {
        this.streakRewardRepository = streakRewardRepository;
    }

    @Override
    public String createStreakReward(StreakRewardDto dto) {
        StreakReward reward = new StreakReward();
        reward.setStreakRewardId(UUID.randomUUID().toString());
        reward.setStreakName(dto.getStreakName());
        reward.setStreakType(dto.getStreakType());
        reward.setDays(dto.getDays());
        reward.setRewardType(dto.getRewardType());
        reward.setXpReward(dto.getXpReward());
        reward.setCoinReward(dto.getCoinReward());
        reward.setUsersEarning(dto.getUsersEarning() != null ? dto.getUsersEarning() : 0);
        reward.setApplicableOn(dto.getApplicableOn());
        reward.setCouponExpiry(dto.getCouponExpiry());
        reward.setMinRedemptionValue(dto.getMinRedemptionValue());
        reward.setActive(dto.getActive() != null ? dto.getActive() : true);
        reward.setCreatedAt(LocalDateTime.now());
        reward.setUpdatedAt(LocalDateTime.now());

        streakRewardRepository.save(reward);
        return "Streak Reward Created Successfully";
    }

    @Override
    public String create(StreakReward streakReward) {
        if (streakReward.getStreakRewardId() == null) {
            streakReward.setStreakRewardId(UUID.randomUUID().toString());
        }
        if (streakReward.getCreatedAt() == null) {
            streakReward.setCreatedAt(LocalDateTime.now());
        }
        streakReward.setUpdatedAt(LocalDateTime.now());

        streakRewardRepository.save(streakReward);
        return "Streak Reward Created Successfully";
    }

    @Override
    public List<StreakRewardDto> getAllStreakRewards() {
        List<StreakReward> rewards = streakRewardRepository.findAll();
        List<StreakRewardDto> dtoList = new ArrayList<>();
        for (StreakReward reward : rewards) {
            dtoList.add(mapToDto(reward));
        }
        return dtoList;
    }

    @Override
    public StreakRewardDto getStreakRewardById(String id) {
        StreakReward reward = streakRewardRepository.findById(id);
        if (reward == null) {
            throw new RuntimeException("Streak Reward Not Found");
        }
        return mapToDto(reward);
    }

    @Override
    public String updateStreakReward(String id, StreakReward streakReward) {
        StreakReward existing = streakRewardRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Streak Reward Not Found");
        }

        existing.setStreakName(streakReward.getStreakName());
        existing.setStreakType(streakReward.getStreakType());
        existing.setDays(streakReward.getDays());
        existing.setRewardType(streakReward.getRewardType());
        existing.setXpReward(streakReward.getXpReward());
        existing.setCoinReward(streakReward.getCoinReward());
        existing.setUsersEarning(streakReward.getUsersEarning());
        existing.setApplicableOn(streakReward.getApplicableOn());
        existing.setCouponExpiry(streakReward.getCouponExpiry());
        existing.setMinRedemptionValue(streakReward.getMinRedemptionValue());
        existing.setActive(streakReward.getActive());
        existing.setUpdatedAt(LocalDateTime.now());

        streakRewardRepository.save(existing);
        return "Streak Reward Updated Successfully";
    }

    @Override
    public String deleteStreakReward(String id) {
        StreakReward reward = streakRewardRepository.findById(id);
        if (reward == null) {
            throw new RuntimeException("Streak Reward Not Found");
        }
        streakRewardRepository.delete(id);
        return "Streak Reward Deleted Successfully";
    }

    private StreakRewardDto mapToDto(StreakReward reward) {
        StreakRewardDto dto = new StreakRewardDto();
        dto.setStreakRewardId(reward.getStreakRewardId());
        dto.setStreakName(reward.getStreakName());
        dto.setStreakType(reward.getStreakType());
        dto.setDays(reward.getDays());
        dto.setRewardType(reward.getRewardType());
        dto.setXpReward(reward.getXpReward());
        dto.setCoinReward(reward.getCoinReward());
        dto.setUsersEarning(reward.getUsersEarning());
        dto.setApplicableOn(reward.getApplicableOn());
        dto.setCouponExpiry(reward.getCouponExpiry());
        dto.setMinRedemptionValue(reward.getMinRedemptionValue());
        dto.setActive(reward.getActive());
        dto.setCreatedAt(reward.getCreatedAt());
        dto.setUpdatedAt(reward.getUpdatedAt());
        return dto;
    }
}
