package com.gamification.streaks.service.Impl;

import com.gamification.streaks.dto.BadgeDto;
import com.gamification.streaks.enums.RewardType;
import com.gamification.streaks.model.Badge;
import com.gamification.streaks.repository.BadgeRepository;
import com.gamification.streaks.service.BadgeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    public BadgeServiceImpl(BadgeRepository badgeRepository){
        this.badgeRepository = badgeRepository;
    }

    public Badge createBadge(BadgeDto badgeDto){
        Badge badge = new Badge();
        badge.setBadgeId(UUID.randomUUID().toString());
        badge.setBadgeName(badgeDto.getBadgeName());
        badge.setBadgeType(badgeDto.getBadgeType());
        badge.setDescription(badgeDto.getDescription());
        badge.setIconUrl(badgeDto.getIconUrl());
        badge.setXpRequired(badgeDto.getXpRequired());
        badge.setXpReward(badgeDto.getXpReward());
        badge.setBadgeTier(badgeDto.getBadgeTier());
        // Default reward type to XP if not specified (since UI shows it fixed to XP for badges)
        badge.setRewardType(badgeDto.getRewardType() != null ? badgeDto.getRewardType() : RewardType.XP);
        badge.setEligibilityRule(badgeDto.getEligibilityRule());
        badge.setActive(badgeDto.getActive());
        badge.setCreatedAt(badgeDto.getCreatedAt() != null ? badgeDto.getCreatedAt() : java.time.LocalDateTime.now());
        return badgeRepository.save(badge);
    }

    public String createBade(Badge badge){
        badge.setBadgeId(UUID.randomUUID().toString());
        if (badge.getRewardType() == null) {
            badge.setRewardType(RewardType.XP);
        }
        if (badge.getCreatedAt() == null) {
            badge.setCreatedAt(java.time.LocalDateTime.now());
        }
        badgeRepository.save(badge);
        return "Badge Created Successfully";
    }

    public BadgeDto getBadgeId(String badgeId){
        Badge badge = badgeRepository.findById(badgeId);
        if(badge == null){
            throw new RuntimeException("Badge Not Found");
        }
        return mapToDto(badge);
    }

    public List<BadgeDto> getAllBadge(){
        List<Badge> badges = badgeRepository.findAll();
        List<BadgeDto> badgeDtos = new ArrayList<>();
        for(Badge badge : badges){
            badgeDtos.add(mapToDto(badge));
        }
        return badgeDtos;
    }

    public String updateBadge(String badgeId, Badge badge){
        Badge existing = badgeRepository.findById(badgeId);
        if(existing == null){
            throw new RuntimeException("Badge Not Found");
        }
        existing.setBadgeName(badge.getBadgeName());
        existing.setBadgeType(badge.getBadgeType());
        existing.setDescription(badge.getDescription());
        existing.setIconUrl(badge.getIconUrl());
        existing.setXpRequired(badge.getXpRequired());
        existing.setXpReward(badge.getXpReward());
        existing.setBadgeTier(badge.getBadgeTier());
        existing.setRewardType(badge.getRewardType() != null ? badge.getRewardType() : RewardType.XP);
        existing.setEligibilityRule(badge.getEligibilityRule());
        existing.setActive(badge.getActive());
        badgeRepository.save(existing);
        return "Badge Updated Successfully";
    }

    public String deleteBadge(String badgeId){
        Badge badge = badgeRepository.findById(badgeId);
        if(badge == null){
            throw new RuntimeException("Badge Not Found");
        }
        badgeRepository.delete(badgeId);
        return "Badge Deleted Successfully";
    }

    private BadgeDto mapToDto(Badge badge){
        BadgeDto dto = new BadgeDto();
        dto.setBadgeId(badge.getBadgeId());
        dto.setBadgeName(badge.getBadgeName());
        dto.setBadgeType(badge.getBadgeType());
        dto.setDescription(badge.getDescription());
        dto.setIconUrl(badge.getIconUrl());
        dto.setXpRequired(badge.getXpRequired());
        dto.setXpReward(badge.getXpReward());
        dto.setBadgeTier(badge.getBadgeTier());
        dto.setRewardType(badge.getRewardType());
        dto.setEligibilityRule(badge.getEligibilityRule());
        dto.setActive(badge.getActive());
        dto.setCreatedAt(badge.getCreatedAt());
        return dto;
    }
}