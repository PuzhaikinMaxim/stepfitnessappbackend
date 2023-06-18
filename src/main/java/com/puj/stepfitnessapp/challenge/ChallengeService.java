package com.puj.stepfitnessapp.challenge;

import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final PlayerStatisticsService playerStatisticsService;

    private final ChallengeMapper challengeMapper = new ChallengeMapper();

    @Autowired
    public ChallengeService(
            ChallengeRepository challengeRepository,
            PlayerStatisticsService playerStatisticsService
    ) {
        this.challengeRepository = challengeRepository;
        this.playerStatisticsService = playerStatisticsService;
    }

    public List<ChallengeDto> getChallengeListByLevel(int challengeLevel) {
        final var result = challengeRepository.getChallengeListByLevel(challengeLevel);

        return result.map(challengeMapper::mapChallengeListToChallengeDtoList).orElse(null);
    }

    public Optional<Challenge> getChallengeById(int challengeId) {
        return challengeRepository.getChallengeByChallengeId(challengeId);
    }

    public List<LevelChallengesDto> getLevelChallengesList() {
        return challengeRepository.getChallengesCountGroupedOnLevel().orElse(new ArrayList<>());
    }

    public ChallengeStatistics getChallengeStatistics(Integer level, Long userId) {
        final var amountOfChallengesByLevel = getChallengeListByLevel(level);
        final var completedChallengesByLevel =
                playerStatisticsService.getStatistics(userId).getCompletedChallenges();
        return new ChallengeStatistics(
                amountOfChallengesByLevel.size(),
                completedChallengesByLevel.get(level).getAmountOfCompletedChallenges()
        );
    }
}
