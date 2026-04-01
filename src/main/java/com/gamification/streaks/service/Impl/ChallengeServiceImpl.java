package com.gamification.streaks.service.Impl;
import com.gamification.streaks.dto.ChallengeDto;
import com.gamification.streaks.model.Challenge;
import com.gamification.streaks.repository.ChallengeRepository;
import com.gamification.streaks.service.ChallengeService;
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
        challenge.setRewardXP(challengeDto.getRewardXP());
        challenge.setRewardBadge(challengeDto.getRewardBadge());
        challenge.setStartDate(challengeDto.getStartDate());
        challenge.setEndDate(challengeDto.getEndDate());
        challenge.setActive(challengeDto.getActive());
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
        existing.setRewardXP(challenge.getRewardXP());
        existing.setRewardBadge(challenge.getRewardBadge());
        existing.setStartDate(challenge.getStartDate());
        existing.setEndDate(challenge.getEndDate());
        existing.setActive(challenge.getActive());
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
        dto.setRewardXP(challenge.getRewardXP());
        dto.setRewardBadge(challenge.getRewardBadge());
        dto.setStartDate(challenge.getStartDate());
        dto.setEndDate(challenge.getEndDate());
        dto.setActive(challenge.getActive());
        return dto;
    }
}