package com.puj.stepfitnessapp.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final ChallengeMapper challengeMapper = new ChallengeMapper();

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public List<ChallengeDto> getChallengeListByLevel(int challengeLevel) {
        final var result = challengeRepository.getChallengeListByLevel(challengeLevel);

        return result.map(challengeMapper::mapChallengeListToChallengeDtoList).orElse(null);
    }

    public Optional<Challenge> getChallengeById(int challengeId) {
        return challengeRepository.getChallengeByChallengeId(challengeId);
    }
}
