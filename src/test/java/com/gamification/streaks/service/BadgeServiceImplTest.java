package com.gamification.streaks.service;

import com.gamification.streaks.dto.BadgeDto;
import com.gamification.streaks.model.Badge;
import com.gamification.streaks.repository.BadgeRepository;
import com.gamification.streaks.service.Impl.BadgeServiceImpl;
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
class BadgeServiceImplTest {

    @Mock
    private BadgeRepository badgeRepository;

    @InjectMocks
    private BadgeServiceImpl badgeService;

    private Badge badge;
    private BadgeDto badgeDto;

    @BeforeEach
    void setUp() {
        badge = new Badge();
        badge.setBadgeId("badge-1");
        badge.setBadgeName("Gold Badge");
        badge.setBadgeType("STREAK");
        badge.setDescription("Awarded for 7-day streak");
        badge.setIconUrl("http://icon.url/gold.png");
        badge.setXpReward(100);
        badge.setEligibilityRule("streak >= 7");
        badge.setActive(true);
        badge.setCreatedAt(LocalDateTime.now());

        badgeDto = new BadgeDto();
        badgeDto.setBadgeName("Gold Badge");
        badgeDto.setBadgeType("STREAK");
        badgeDto.setDescription("Awarded for 7-day streak");
        badgeDto.setIconUrl("http://icon.url/gold.png");
        badgeDto.setXpReward(100);
        badgeDto.setEligibilityRule("streak >= 7");
        badgeDto.setActive(true);
        badgeDto.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createBadge_shouldReturnSavedBadge() {
        // createBadge(BadgeDto) maps dto -> Badge, assigns UUID, saves and returns it
        when(badgeRepository.save(any(Badge.class))).thenAnswer(inv -> inv.getArgument(0));

        Badge result = badgeService.createBadge(badgeDto);

        assertThat(result).isNotNull();
        assertThat(result.getBadgeName()).isEqualTo("Gold Badge");
        assertThat(result.getBadgeId()).isNotNull(); // UUID auto-assigned
        verify(badgeRepository).save(any(Badge.class));
    }

    @Test
    void createBade_shouldReturnSuccessMessage() {
        // createBade(Badge) assigns UUID and saves
        when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

        String result = badgeService.createBade(badge);

        assertThat(result).isEqualTo("Badge Created Successfully");
        verify(badgeRepository).save(badge);
    }

    @Test
    void getBadgeId_shouldReturnDto_whenBadgeExists() {
        when(badgeRepository.findById("badge-1")).thenReturn(badge);

        BadgeDto result = badgeService.getBadgeId("badge-1");

        assertThat(result.getBadgeId()).isEqualTo("badge-1");
        assertThat(result.getBadgeName()).isEqualTo("Gold Badge");
        assertThat(result.getXpReward()).isEqualTo(100);
    }

    @Test
    void getBadgeId_shouldThrow_whenBadgeNotFound() {
        when(badgeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> badgeService.getBadgeId("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Badge Not Found");
    }

    @Test
    void getAllBadge_shouldReturnMappedDtoList() {
        when(badgeRepository.findALl()).thenReturn(List.of(badge));

        List<BadgeDto> result = badgeService.getAllBadge();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBadgeName()).isEqualTo("Gold Badge");
    }

    @Test
    void getAllBadge_shouldReturnEmptyList_whenNoBadges() {
        when(badgeRepository.findALl()).thenReturn(List.of());

        assertThat(badgeService.getAllBadge()).isEmpty();
    }

    @Test
    void updateBadge_shouldUpdateFieldsAndReturnSuccess() {
        Badge updated = new Badge();
        updated.setBadgeName("Platinum");
        updated.setBadgeType("PERFORMANCE");
        updated.setDescription("Updated");
        updated.setIconUrl("http://new.url");
        updated.setXpReward(200);
        updated.setEligibilityRule("streak >= 30");
        updated.setActive(false);

        when(badgeRepository.findById("badge-1")).thenReturn(badge);
        when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

        String result = badgeService.updateBadge("badge-1", updated);

        assertThat(result).isEqualTo("Badge Updated Successfully");
        assertThat(badge.getBadgeName()).isEqualTo("Platinum"); // field was mutated
        verify(badgeRepository).save(badge);
    }

    @Test
    void updateBadge_shouldThrow_whenBadgeNotFound() {
        when(badgeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> badgeService.updateBadge("missing", badge))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Badge Not Found");
    }

    @Test
    void deleteBadge_shouldReturnSuccess_whenBadgeExists() {
        when(badgeRepository.findById("badge-1")).thenReturn(badge);

        String result = badgeService.deleteBadge("badge-1");

        assertThat(result).isEqualTo("Badge Deleted Successfully");
        verify(badgeRepository).delete("badge-1");
    }

    @Test
    void deleteBadge_shouldThrow_whenBadgeNotFound() {
        when(badgeRepository.findById("missing")).thenReturn(null);

        assertThatThrownBy(() -> badgeService.deleteBadge("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Badge Not Found");
    }
}
