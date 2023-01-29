package com.puj.stepfitnessapp.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Optional<List<Challenge>> getChallengeListByLevel(int challengeLevel) {
        return challengeRepository.getChallengeListByLevel(challengeLevel);
    }

    public Optional<Challenge> getChallengeById(int challengeId) {
        return challengeRepository.getChallengeByChallengeId(challengeId);
    }
}
