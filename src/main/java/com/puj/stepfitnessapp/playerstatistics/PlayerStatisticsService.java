package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.challenge.Challenge;
import com.puj.stepfitnessapp.challengelevel.ChallengeLevel;
import com.puj.stepfitnessapp.challengelevel.ChallengeLevelService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlayerStatisticsService {

    private final PlayerStatisticsRepository repository;

    private final ChallengeLevelService service;


    @Autowired
    public PlayerStatisticsService(
            PlayerStatisticsRepository repository,
            ChallengeLevelService service
    ) {
        this.repository = repository;
        this.service = service;
    }

    public void addStatistics(Player player) {
        final var challengesList = createCompletedChallengesList();
        final var playerStatistics = new PlayerStatistics(
                player.getUser_id(),
                player,
                challengesList,
                0,
                0
        );
        repository.save(playerStatistics);
    }

    public PlayerStatistics getStatistics(Player player) {
        return getPlayerStatistics(player.getUser_id());
    }

    public void addCompletedChallenge(Player player, Challenge challenge) {
        final var playerStatistics = getPlayerStatistics(player.getUser_id());
        playerStatistics.getCompletedChallenges().get(
                challenge.getLevel().getChallengeLevel()-1
        ).addCompletedChallenge(challenge.getChallengeId());
        repository.save(playerStatistics);
    }

    public void addAmountOfSteps(Player player, int amountOfStepsPassed) {
        final var playerStatistics = getPlayerStatistics(player.getUser_id());
        final var amountOfSteps = playerStatistics.getAmountOfSteps() + amountOfStepsPassed;
        playerStatistics.setAmountOfSteps(amountOfSteps);
        repository.save(playerStatistics);
    }

    public void incrementAmountOfDuelsWon(Player player) {
        final var playerStatistics = getPlayerStatistics(player.getUser_id());
        final var amountOfDuelsWon = playerStatistics.getAmountOfDuelsWon() + 1;
        playerStatistics.setAmountOfDuelsWon(amountOfDuelsWon);
        repository.save(playerStatistics);
    }

    private PlayerStatistics getPlayerStatistics(Long userId) {
        return repository.findById(userId).get();
    }

    private ArrayList<CompletedChallenges> createCompletedChallengesList() {
        var list = service.getChallengeLevelList();
        var completedChallengesList = new ArrayList<CompletedChallenges>();
        for (ChallengeLevel l: list) {
            var completedChallenges = new CompletedChallenges(l.getChallengeLevel());
            completedChallengesList.add(completedChallenges);
        }
        return completedChallengesList;
    }
}
