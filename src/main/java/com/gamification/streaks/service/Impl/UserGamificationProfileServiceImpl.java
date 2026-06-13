package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.UserGamificationProfileDto;
import com.gamification.streaks.model.UserGamificationProfile;
import com.gamification.streaks.repository.UserGamificationProfileRepository;
import com.gamification.streaks.service.UserGamificationProfileService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class UserGamificationProfileServiceImpl implements UserGamificationProfileService {
    private final UserGamificationProfileRepository userGamificationProfileRepository;
    public UserGamificationProfileServiceImpl(UserGamificationProfileRepository userGamificationProfileRepository){
        this.userGamificationProfileRepository = userGamificationProfileRepository;
    }
    public UserGamificationProfile createUserGamification(UserGamificationProfileDto dto){

        UserGamificationProfile profile = new UserGamificationProfile();
        profile.setUserId(UUID.randomUUID().toString());
        profile.setRole(dto.getRole());
        profile.setTotalXp(dto.getTotalXp());
        profile.setCurrentLevel(dto.getCurrentLevel());
        profile.setLongestStreak(dto.getLongestStreak());
        profile.setLastActivityData(dto.getLastActivityData());
        profile.setTotalBadges(dto.getTotalBadges());
        profile.setTotalReward(dto.getTotalReward());
        profile.setCoinBalance(dto.getCoinBalance());
        profile.setCreatedAt(dto.getCreatedAt());
        profile.setUpdatedAt(dto.getUpdatedAt());
        return userGamificationProfileRepository.save(profile);
    }
    public String createUserGamificationProfile(UserGamificationProfile userGamificationProfile){
        userGamificationProfile.setUserId(UUID.randomUUID().toString());
        userGamificationProfileRepository.save(userGamificationProfile);
        return "User Gamification Profile Created Successfully";
    }
    public UserGamificationProfileDto getUserGamificationProfileById(String userId){
        UserGamificationProfile profile = userGamificationProfileRepository.findById(userId);
        if(profile == null){
            throw new RuntimeException("User Gamification Profile Not Found");
        }
        return mapToDto(profile);
    }
    public List<UserGamificationProfileDto> getAllUserGamificationProfiles(){
        List<UserGamificationProfile> profiles = userGamificationProfileRepository.findAll();
        List<UserGamificationProfileDto> dtoList = new ArrayList<>();
        for(UserGamificationProfile profile : profiles){
            dtoList.add(mapToDto(profile));
        }
        return dtoList;
    }

    public String updateUserGamificationProfile(String userId, UserGamificationProfile userGamificationProfile){
        UserGamificationProfile existing = userGamificationProfileRepository.findById(userId);
        if(existing == null){
            throw new RuntimeException("User Gamification Profile Not Found");
        }
        existing.setRole(userGamificationProfile.getRole());
        existing.setTotalXp(userGamificationProfile.getTotalXp());
        existing.setCurrentLevel(userGamificationProfile.getCurrentLevel());
        existing.setLongestStreak(userGamificationProfile.getLongestStreak());
        existing.setLastActivityData(userGamificationProfile.getLastActivityData());
        existing.setTotalBadges(userGamificationProfile.getTotalBadges());
        existing.setTotalReward(userGamificationProfile.getTotalReward());
        existing.setCoinBalance(userGamificationProfile.getCoinBalance());
        existing.setUpdatedAt(userGamificationProfile.getUpdatedAt());
        userGamificationProfileRepository.save(existing);
        return "User Gamification Profile Updated Successfully";
    }
    public String deleteUserGamificationProfile(String userId){
        UserGamificationProfile profile = userGamificationProfileRepository.findById(userId);
        if(profile == null){
            throw new RuntimeException("User Gamification Profile Not Found");
        }
        userGamificationProfileRepository.delete(String.valueOf(profile));
        return "User Gamification Profile Deleted Successfully";
    }
    private UserGamificationProfileDto mapToDto(UserGamificationProfile profile){
        UserGamificationProfileDto dto = new UserGamificationProfileDto();
        dto.setUserId(profile.getUserId());
        dto.setRole(profile.getRole());
        dto.setTotalXp(profile.getTotalXp());
        dto.setCurrentLevel(profile.getCurrentLevel());
        dto.setLongestStreak(profile.getLongestStreak());
        dto.setLastActivityData(profile.getLastActivityData());
        dto.setTotalBadges(profile.getTotalBadges());
        dto.setTotalReward(profile.getTotalReward());
        dto.setCoinBalance(profile.getCoinBalance());
        dto.setCreatedAt(profile.getCreatedAt());
        dto.setUpdatedAt(profile.getUpdatedAt());
        return dto;
    }
}