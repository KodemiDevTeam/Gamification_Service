package com.gamification.streaks.service.impl;

import com.gamification.streaks.dto.UserBadgeDto;
import com.gamification.streaks.execption.ResourceNotFoundException;
import com.gamification.streaks.model.UserBadge;
import com.gamification.streaks.repository.UserBadgeRepository;
import com.gamification.streaks.service.UserBadgeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserBadgeServiceImpl implements UserBadgeService {

    private final UserBadgeRepository userBadgeRepository;

    public UserBadgeServiceImpl(UserBadgeRepository userBadgeRepository) {
        this.userBadgeRepository = userBadgeRepository;
    }

    public String createUserBadge(UserBadgeDto userBadgeDto) {
        UserBadge userBadge = new UserBadge();
        userBadge.setUserBadgeId(UUID.randomUUID().toString());
        userBadge.setUserId(userBadgeDto.getUserId());
        userBadge.setBadgeId(userBadgeDto.getBadgeId());
        userBadge.setBadgeName(userBadgeDto.getBadgeName());
        userBadge.setCreatedData(LocalDateTime.now());
        userBadge.setXpAwarded(userBadgeDto.getXpAwarded());
        userBadgeRepository.save(userBadge);
        return "User Badge Created Successfully";
    }

    public String create(UserBadge userBadge) {
        userBadge.setUserBadgeId(UUID.randomUUID().toString());
        userBadgeRepository.save(userBadge);
        return "User Badge Created Successfully";
    }

    public List<UserBadgeDto> getAllUserBadge() {
        List<UserBadge> userBadges = userBadgeRepository.findAll();
        List<UserBadgeDto> dtoList = new ArrayList<>();
        for (UserBadge badge : userBadges) {
            dtoList.add(mapToDto(badge));
        }
        return dtoList;
    }

    public String updateUserBadge(String userBadgeId, UserBadge userBadge) {
        UserBadge existing = userBadgeRepository.findById(userBadgeId);
        if (existing == null) {
            throw new ResourceNotFoundException("User Badge Not Found");
        }
        existing.setUserId(userBadge.getUserId());
        existing.setBadgeId(userBadge.getBadgeId());
        existing.setBadgeName(userBadge.getBadgeName());
        existing.setCreatedData(userBadge.getCreatedData());
        existing.setXpAwarded(userBadge.getXpAwarded());
        userBadgeRepository.save(existing);
        return "User Badge Updated Successfully";
    }

    public String deleteUserBadge(String userBadgeId) {
        UserBadge badge = userBadgeRepository.findById(userBadgeId);
        if (badge == null) {
            throw new ResourceNotFoundException("User Badge Not Found");
        }
        userBadgeRepository.delete(userBadgeId);
        return "User Badge Deleted Successfully";
    }

    private UserBadgeDto mapToDto(UserBadge userBadge) {
        UserBadgeDto dto = new UserBadgeDto();
        dto.setUserBadgeId(userBadge.getUserBadgeId());
        dto.setUserId(userBadge.getUserId());
        dto.setBadgeId(userBadge.getBadgeId());
        dto.setBadgeName(userBadge.getBadgeName());
        dto.setCreatedData(userBadge.getCreatedData());
        dto.setXpAwarded(userBadge.getXpAwarded());
        return dto;
    }
}
