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
    private final com.gamification.streaks.repository.XpTransactionRepository xpTransactionRepository;

    public UserGamificationProfileServiceImpl(
            UserGamificationProfileRepository userGamificationProfileRepository,
            com.gamification.streaks.repository.XpTransactionRepository xpTransactionRepository){
        this.userGamificationProfileRepository = userGamificationProfileRepository;
        this.xpTransactionRepository = xpTransactionRepository;
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

    @Override
    public com.gamification.streaks.dto.CheckoutCalculationDto checkoutCalculate(String userId) {
        UserGamificationProfile profile = userGamificationProfileRepository.findById(userId);
        if (profile == null) {
            throw new RuntimeException("User Gamification Profile Not Found");
        }
        com.gamification.streaks.dto.CheckoutCalculationDto calculation = new com.gamification.streaks.dto.CheckoutCalculationDto();
        calculation.setUserId(userId);
        calculation.setTotalXp(profile.getTotalXp() != null ? profile.getTotalXp() : 0);
        calculation.setCoinBalance(profile.getCoinBalance() != null ? profile.getCoinBalance() : 0);
        calculation.setPotentialCoinsFromXp(calculation.getTotalXp() / 100);
        calculation.setDiscountValue(calculation.getCoinBalance() * 10.0);
        return calculation;
    }

    @Override
    public String convertXpToCoins(String userId, Integer coins) {
        UserGamificationProfile profile = userGamificationProfileRepository.findById(userId);
        if (profile == null) {
            throw new RuntimeException("User Gamification Profile Not Found");
        }
        int requiredXp = coins * 100;
        int currentXp = profile.getTotalXp() != null ? profile.getTotalXp() : 0;
        if (currentXp < requiredXp) {
            throw new RuntimeException("Insufficient XP Balance to convert to " + coins + " Coins");
        }
        profile.setTotalXp(currentXp - requiredXp);
        profile.setCoinBalance((profile.getCoinBalance() != null ? profile.getCoinBalance() : 0) + coins);
        profile.setUpdatedAt(java.time.LocalDateTime.now());
        userGamificationProfileRepository.save(profile);

        com.gamification.streaks.model.XpTransaction transaction = new com.gamification.streaks.model.XpTransaction();
        transaction.setXpTransactionId(UUID.randomUUID().toString());
        transaction.setUserId(userId);
        transaction.setXpAmount(-requiredXp);
        transaction.setXpSource("XP_TO_COIN_CONVERSION");
        transaction.setReferenceId(profile.getUserId());
        transaction.setDescription("Converted " + requiredXp + " XP into " + coins + " Coins");
        transaction.setCreatedAt(java.time.LocalDateTime.now().toString());
        xpTransactionRepository.save(transaction);

        return "Converted " + requiredXp + " XP to " + coins + " Coins Successfully";
    }

    @Override
    public Double redeemCoins(String userId, Integer coins) {
        UserGamificationProfile profile = userGamificationProfileRepository.findById(userId);
        if (profile == null) {
            throw new RuntimeException("User Gamification Profile Not Found");
        }
        int currentCoins = profile.getCoinBalance() != null ? profile.getCoinBalance() : 0;
        if (currentCoins < coins) {
            throw new RuntimeException("Insufficient Coin Balance to redeem " + coins + " Coins");
        }
        profile.setCoinBalance(currentCoins - coins);
        profile.setUpdatedAt(java.time.LocalDateTime.now());
        userGamificationProfileRepository.save(profile);

        com.gamification.streaks.model.XpTransaction transaction = new com.gamification.streaks.model.XpTransaction();
        transaction.setXpTransactionId(UUID.randomUUID().toString());
        transaction.setUserId(userId);
        transaction.setXpAmount(0);
        transaction.setXpSource("COIN_REDEMPTION");
        transaction.setReferenceId(profile.getUserId());
        double discount = coins * 10.0;
        transaction.setDescription("Redeemed " + coins + " Coins for ₹" + discount + " discount");
        transaction.setCreatedAt(java.time.LocalDateTime.now().toString());
        xpTransactionRepository.save(transaction);

        return discount;
    }
}