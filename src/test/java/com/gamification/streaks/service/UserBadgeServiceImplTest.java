package com.gamification.streaks.service;

import com.gamification.streaks.dto.UserBadgeDto;
import com.gamification.streaks.model.UserBadge;
import com.gamification.streaks.repository.UserBadgeRepository;
import com.gamification.streaks.service.impl.UserBadgeServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBadgeServiceImplTest {

    @Mock
    private UserBadgeRepository userBadgeRepository;

    @InjectMocks
    private UserBadgeServiceimpl userBadgeService;

    private UserBadge userBadge;
    private UserBadgeDto userBadgeDto;

    @BeforeEach
    void setUp() {
        userBadge = new UserBadge();
        userBadge.setUserBadgeId("ub-1");
        userBadge.setUserId("user-1");
        userBadge.setBadgeId("badge-1");
        userBadge.setBadgeName("Gold Badge");
        userBadge.setCreatedData(LocalDateTime.now());
        userBadge.setXpAwarded(100);

        userBadgeDto = new UserBadgeDto();
        userBadgeDto.setUserId("user-1");
        userBadgeDto.setBadgeId("badge-1");
        userBadgeDto.setBadgeName("Gold Badge");
        userBadgeDto.setCreatedData(LocalDateTime.now());
        userBadgeDto.setXpAwarded(100);
    }

    @Test
    void createUserBadge_shouldReturnSuccess() {
        when(userBadgeRepository.save(any(UserBadge.class))).thenReturn(userBadge);

        String result = userBadgeService.createUserBadge(userBadgeDto);

        assertThat(result).isEqualTo("User Badge Created Successfully");
        verify(userBadgeRepository).save(any(UserBadge.class));
    }

    @Test
    void create_shouldAssignUuidAndReturnSuccess() {
        when(userBadgeRepository.save(any(UserBadge.class))).thenReturn(userBadge);

        String result = userBadgeService.create(userBadge);

        assertThat(result).isEqualTo("User Badge Created Successfully");
        assertThat(userBadge.getUserBadgeId()).isNotNull();
        verify(userBadgeRepository).save(userBadge);
    }

    @Test
    void getAllUserBadge_shouldReturnMappedDtoList() {
        when(userBadgeRepository.findAll()).thenReturn(List.of(userBadge));

        List<UserBadgeDto> result = userBadgeService.getAllUserBadge();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo("user-1");
        assertThat(result.get(0).getBadgeName()).isEqualTo("Gold Badge");
        assertThat(result.get(0).getXpAwarded()).isEqualTo(100);
    }

    @Test
    void getAllUserBadge_shouldReturnEmptyList_whenNoBadges() {
        when(userBadgeRepository.findAll()).thenReturn(List.of());

        assertThat(userBadgeService.getAllUserBadge()).isEmpty();
    }

    @Test
    void updateUserBadge_shouldUpdateFieldsAndReturnSuccess() {
        UserBadge updated = new UserBadge();
        updated.setUserId("user-2");
        updated.setBadgeId("badge-2");
        updated.setBadgeName("Platinum Badge");
        updated.setCreatedData(LocalDateTime.now());
        updated.setXpAwarded(200);

        when(userBadgeRepository.findById("ub-1")).thenReturn(userBadge);
        when(userBadgeRepository.save(any(UserBadge.class))).thenReturn(userBadge);

        String result = userBadgeService.updateUserBadge("ub-1", updated);

        assertThat(result).isEqualTo("User Badge Updated Successfully");
        assertThat(userBadge.getBadgeName()).isEqualTo("Platinum Badge");
        assertThat(userBadge.getXpAwarded()).isEqualTo(200);
        verify(userBadgeRepository).save(userBadge);
    }

    @Test
    void updateUserBadge_shouldThrow_whenNotFound() {
        when(userBadgeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> userBadgeService.updateUserBadge("missing", userBadge))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Badge Not Found");
    }

    @Test
    void deleteUserBadge_shouldReturnSuccess_whenExists() {
        when(userBadgeRepository.findById("ub-1")).thenReturn(userBadge);

        String result = userBadgeService.deleteUserBadge("ub-1");

        assertThat(result).isEqualTo("User Badge Deleted Successfully");
        verify(userBadgeRepository).delete("ub-1");
    }

    @Test
    void deleteUserBadge_shouldThrow_whenNotFound() {
        when(userBadgeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> userBadgeService.deleteUserBadge("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Badge Not Found");
    }
}
