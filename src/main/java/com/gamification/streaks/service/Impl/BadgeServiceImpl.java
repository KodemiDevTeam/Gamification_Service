package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.BadgeDto;
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
        badge.setXpReward(badgeDto.getXpReward());
        badge.setEligibilityRule(badgeDto.getEligibilityRule());
        badge.setActive(badgeDto.getActive());
        badge.setCreatedAt(badgeDto.getCreatedAt());
        return badgeRepository.save(badge);
    }

    public String createBade(Badge badge){
        badge.setBadgeId(UUID.randomUUID().toString());
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
        existing.setXpReward(badge.getXpReward());
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
        dto.setXpReward(badge.getXpReward());
        dto.setEligibilityRule(badge.getEligibilityRule());
        dto.setActive(badge.getActive());
        dto.setCreatedAt(badge.getCreatedAt());
        return dto;
    }
}