package com.gamification.streaks.service;

import com.gamification.streaks.dto.UserGamificationProfileDto;
import com.gamification.streaks.model.UserGamificationProfile;
import com.gamification.streaks.repository.UserGamificationProfileRepository;
import com.gamification.streaks.service.Impl.UserGamificationProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGamificationProfileServiceImplTest {

    @Mock
    private UserGamificationProfileRepository userGamificationProfileRepository;

    @InjectMocks
    private UserGamificationProfileServiceImpl userGamificationProfileService;

    private UserGamificationProfile profile;
    private UserGamificationProfileDto profileDto;

    @BeforeEach
    void setUp() {
        profile = new UserGamificationProfile();
        profile.setUserId("user-1");
        profile.setRole("LEARNER");
        profile.setTotalXp(500);
        profile.setCurrentLevel(3);
        profile.setLongestStreak(10);
        profile.setLastActivityData(LocalDate.now());
        profile.setTotalBadges(5);
        profile.setTotalReward(2);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        profileDto = new UserGamificationProfileDto();
        profileDto.setRole("LEARNER");
        profileDto.setTotalXp(500);
        profileDto.setCurrentLevel(3);
        profileDto.setLongestStreak(10);
        profileDto.setLastActivityData(LocalDate.now());
        profileDto.setTotalBadges(5);
        profileDto.setTotalReward(2);
        profileDto.setCreatedAt(LocalDateTime.now());
        profileDto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createUserGamification_shouldReturnSavedProfile() {
        // createUserGamification(dto) maps dto -> profile, assigns UUID, saves and returns it
        when(userGamificationProfileRepository.save(any(UserGamificationProfile.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        UserGamificationProfile result = userGamificationProfileService.createUserGamification(profileDto);

        assertThat(result).isNotNull();
        assertThat(result.getRole()).isEqualTo("LEARNER");
        assertThat(result.getTotalXp()).isEqualTo(500);
        assertThat(result.getUserId()).isNotNull(); // UUID assigned
        verify(userGamificationProfileRepository).save(any(UserGamificationProfile.class));
    }

    @Test
    void createUserGamificationProfile_shouldAssignUuidAndReturnSuccess() {
        when(userGamificationProfileRepository.save(any(UserGamificationProfile.class))).thenReturn(profile);

        String result = userGamificationProfileService.createUserGamificationProfile(profile);

        assertThat(result).isEqualTo("User Gamification Profile Created Successfully");
        assertThat(profile.getUserId()).isNotNull();
        verify(userGamificationProfileRepository).save(profile);
    }

    @Test
    void getUserGamificationProfileById_shouldReturnDto_whenExists() {
        when(userGamificationProfileRepository.findById("user-1")).thenReturn(profile);

        UserGamificationProfileDto result = userGamificationProfileService.getUserGamificationProfileById("user-1");

        assertThat(result.getUserId()).isEqualTo("user-1");
        assertThat(result.getRole()).isEqualTo("LEARNER");
        assertThat(result.getTotalXp()).isEqualTo(500);
        assertThat(result.getCurrentLevel()).isEqualTo(3);
    }

    @Test
    void getUserGamificationProfileById_shouldThrow_whenNotFound() {
        when(userGamificationProfileRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> userGamificationProfileService.getUserGamificationProfileById("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Gamification Profile Not Found");
    }

    @Test
    void getAllUserGamificationProfiles_shouldReturnMappedDtoList() {
        when(userGamificationProfileRepository.findAll()).thenReturn(List.of(profile));

        List<UserGamificationProfileDto> result = userGamificationProfileService.getAllUserGamificationProfiles();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo("user-1");
        assertThat(result.get(0).getTotalBadges()).isEqualTo(5);
    }

    @Test
    void getAllUserGamificationProfiles_shouldReturnEmptyList_whenNoProfiles() {
        when(userGamificationProfileRepository.findAll()).thenReturn(List.of());

        assertThat(userGamificationProfileService.getAllUserGamificationProfiles()).isEmpty();
    }

    @Test
    void updateUserGamificationProfile_shouldUpdateFieldsAndReturnSuccess() {
        UserGamificationProfile updated = new UserGamificationProfile();
        updated.setRole("TRAINER");
        updated.setTotalXp(1000);
        updated.setCurrentLevel(5);
        updated.setLongestStreak(20);
        updated.setLastActivityData(LocalDate.now());
        updated.setTotalBadges(10);
        updated.setTotalReward(5);
        updated.setUpdatedAt(LocalDateTime.now());

        when(userGamificationProfileRepository.findById("user-1")).thenReturn(profile);
        when(userGamificationProfileRepository.save(any(UserGamificationProfile.class))).thenReturn(profile);

        String result = userGamificationProfileService.updateUserGamificationProfile("user-1", updated);

        assertThat(result).isEqualTo("User Gamification Profile Updated Successfully");
        assertThat(profile.getRole()).isEqualTo("TRAINER");
        assertThat(profile.getTotalXp()).isEqualTo(1000);
        verify(userGamificationProfileRepository).save(profile);
    }

    @Test
    void updateUserGamificationProfile_shouldThrow_whenNotFound() {
        when(userGamificationProfileRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> userGamificationProfileService.updateUserGamificationProfile("missing", profile))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Gamification Profile Not Found");
    }

    @Test
    void deleteUserGamificationProfile_shouldThrow_whenNotFound() {
        when(userGamificationProfileRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> userGamificationProfileService.deleteUserGamificationProfile("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Gamification Profile Not Found");
    }
}
