package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.ChallengeDto;
import com.gamification.streaks.model.Challenge;
import com.gamification.streaks.repository.ChallengeRepository;
import com.gamification.streaks.service.ChallengeService;
import com.gamification.streaks.model.RewardTier;
import com.gamification.streaks.dto.RewardTierDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    public ChallengeServiceImpl(ChallengeRepository challengeRepository){
        this.challengeRepository = challengeRepository;
    }
    public String createChallenge(ChallengeDto challengeDto){
        Challenge challenge = new Challenge();
        challenge.setChallengeId(UUID.randomUUID().toString());
        challenge.setChallengeName(challengeDto.getChallengeName());
        challenge.setChallengeType(challengeDto.getChallengeType());
        challenge.setDescription(challengeDto.getDescription());
        challenge.setTargetType(challengeDto.getTargetType());
        challenge.setTargetCount(challengeDto.getTargetCount());
        challenge.setRewardXP(challengeDto.getRewardXP());
        challenge.setRewardBadge(challengeDto.getRewardBadge());
        challenge.setStartDate(challengeDto.getStartDate());
        challenge.setEndDate(challengeDto.getEndDate());
        challenge.setActive(challengeDto.getActive());
        challenge.setUserId(challengeDto.getUserId());
        challenge.setTransactionId(challengeDto.getTransactionId());
        challenge.setMaxParticipants(challengeDto.getMaxParticipants());
        challenge.setCurrentParticipants(challengeDto.getCurrentParticipants());
        challenge.setRewardTiers(mapTiersToModel(challengeDto.getRewardTiers()));
        challengeRepository.save(challenge);

        return "Challenge Created Successfully";
    }
    public String create(Challenge challenge){
        challenge.setChallengeId(UUID.randomUUID().toString());
        challengeRepository.save(challenge);
        return "Challenge Created Successfully";
    }
    public List<ChallengeDto> getAllChallenge(){
        List<Challenge> challenges = challengeRepository.findAll();
        List<ChallengeDto> dtoList = new ArrayList<>();
        for(Challenge challenge : challenges){
            dtoList.add(mapToDto(challenge));
        }

        return dtoList;
    }
    public String updateChallenge(String challengeId, Challenge challenge){
        Challenge existing = challengeRepository.findById(challengeId);
        if(existing == null){
            throw new RuntimeException("Challenge Not Found");
        }
        existing.setChallengeName(challenge.getChallengeName());
        existing.setChallengeType(challenge.getChallengeType());
        existing.setDescription(challenge.getDescription());
        existing.setTargetType(challenge.getTargetType());
        existing.setTargetCount(challenge.getTargetCount());
        existing.setRewardXP(challenge.getRewardXP());
        existing.setRewardBadge(challenge.getRewardBadge());
        existing.setStartDate(challenge.getStartDate());
        existing.setEndDate(challenge.getEndDate());
        existing.setActive(challenge.getActive());
        existing.setUserId(challenge.getUserId());
        existing.setTransactionId(challenge.getTransactionId());
        existing.setMaxParticipants(challenge.getMaxParticipants());
        existing.setCurrentParticipants(challenge.getCurrentParticipants());
        existing.setRewardTiers(challenge.getRewardTiers());
        challengeRepository.save(existing);
        return "Challenge Updated Successfully";
    }
    public String deleteChallenge(String challengeId){
        Challenge challenge = challengeRepository.findById(challengeId);
        if(challenge == null){
            throw new RuntimeException("Challenge Not Found");
        }
        challengeRepository.delete(challengeId);
        return "Challenge Deleted Successfully";
    }
    private ChallengeDto mapToDto(Challenge challenge){
        ChallengeDto dto = new ChallengeDto();
        dto.setChallengeId(challenge.getChallengeId());
        dto.setChallengeName(challenge.getChallengeName());
        dto.setChallengeType(challenge.getChallengeType());
        dto.setDescription(challenge.getDescription());
        dto.setTargetType(challenge.getTargetType());
        dto.setTargetCount(challenge.getTargetCount());
        dto.setRewardXP(challenge.getRewardXP());
        dto.setRewardBadge(challenge.getRewardBadge());
        dto.setStartDate(challenge.getStartDate());
        dto.setEndDate(challenge.getEndDate());
        dto.setActive(challenge.getActive());
        dto.setUserId(challenge.getUserId());
        dto.setTransactionId(challenge.getTransactionId());
        dto.setMaxParticipants(challenge.getMaxParticipants());
        dto.setCurrentParticipants(challenge.getCurrentParticipants());
        dto.setRewardTiers(mapTiersToDto(challenge.getRewardTiers()));
        dto.setStatus(calculateStatus(challenge));
        return dto;
    }

    private List<RewardTier> mapTiersToModel(List<RewardTierDto> dtos) {
        if (dtos == null) return null;
        List<RewardTier> list = new ArrayList<>();
        for (RewardTierDto d : dtos) {
            RewardTier t = new RewardTier();
            t.setTierName(d.getTierName());
            t.setMinScore(d.getMinScore());
            t.setRewardPoints(d.getRewardPoints());
            t.setRewardBadge(d.getRewardBadge());
            list.add(t);
        }
        return list;
    }

    private List<RewardTierDto> mapTiersToDto(List<RewardTier> models) {
        if (models == null) return null;
        List<RewardTierDto> list = new ArrayList<>();
        for (RewardTier m : models) {
            RewardTierDto d = new RewardTierDto();
            d.setTierName(m.getTierName());
            d.setMinScore(m.getMinScore());
            d.setRewardPoints(m.getRewardPoints());
            d.setRewardBadge(m.getRewardBadge());
            list.add(d);
        }
        return list;
    }

    private String calculateStatus(Challenge challenge) {
        if (challenge.getActive() == null || !challenge.getActive()) {
            return "Expired";
        }
        java.time.LocalDate now = java.time.LocalDate.now();
        if (challenge.getStartDate() != null && now.isBefore(challenge.getStartDate())) {
            return "Upcoming";
        }
        if (challenge.getEndDate() != null) {
            if (now.isAfter(challenge.getEndDate())) {
                return "Expired";
            }
            if (now.plusDays(7).isAfter(challenge.getEndDate())) {
                return "Ending Soon";
            }
        }
        return "Active";
    }
}