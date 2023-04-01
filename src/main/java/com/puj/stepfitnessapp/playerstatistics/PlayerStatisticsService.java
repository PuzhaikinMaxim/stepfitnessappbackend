package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.achievement.AchievementService;
import com.puj.stepfitnessapp.challengelevel.ChallengeLevel;
import com.puj.stepfitnessapp.challengelevel.ChallengeLevelService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PlayerStatisticsService {

    private final PlayerStatisticsRepository repository;

    private final ChallengeLevelService service;

    private final AchievementService achievementService;


    @Autowired
    public PlayerStatisticsService(
            PlayerStatisticsRepository repository,
            ChallengeLevelService service,
            AchievementService achievementService
    ) {
        this.repository = repository;
        this.service = service;
        this.achievementService = achievementService;
    }

    public void addStatistics(Player player) {
        final var challengesList = createCompletedChallengesList();
        final var playerStatistics = new PlayerStatistics(
                player.getUser_id(),
                player,
                challengesList,
                new HashSet<>(),
                0,
                0
        );
        repository.save(playerStatistics);
    }

    public PlayerStatistics getStatistics(Long userId) {
        return getPlayerStatistics(userId);
    }

    public void addCompletedChallenge(Player player, int challengeLevel, Long challengeId) {
        final var playerStatistics = getPlayerStatistics(player.getUser_id());
        addChallengeLevelsIfNotExists(challengeLevel, playerStatistics.getCompletedChallenges());
        var oldAmountOfChallenges = playerStatistics.getCompletedChallenges().size();
        playerStatistics.getCompletedChallenges().get(
                challengeLevel-1
        ).addCompletedChallenge(challengeId);
        var newAmountOfChallenges = playerStatistics.getCompletedChallenges().size();
        achievementService.addChallengesAmountAchievements(playerStatistics,newAmountOfChallenges,oldAmountOfChallenges);
        repository.save(playerStatistics);
    }

    public void addAmountOfSteps(Player player, int amountOfStepsPassed) {
        final var playerStatistics = getPlayerStatistics(player.getUser_id());
        final var newAmountOfSteps = playerStatistics.getAmountOfSteps() + amountOfStepsPassed;
        achievementService.addStepCountCompletedAchievements(
                playerStatistics,
                newAmountOfSteps,
                playerStatistics.getAmountOfSteps()
        );
        playerStatistics.setAmountOfSteps(newAmountOfSteps);
        repository.save(playerStatistics);
    }

    public void incrementAmountOfDuelsWon(Player player) {
        final var playerStatistics = getPlayerStatistics(player.getUser_id());
        final var amountOfDuelsWon = playerStatistics.getAmountOfDuelsWon() + 1;
        achievementService.addDuelAmountAchievements(
                playerStatistics,
                amountOfDuelsWon,
                playerStatistics.getAmountOfDuelsWon());
        playerStatistics.setAmountOfDuelsWon(amountOfDuelsWon);
        repository.save(playerStatistics);
    }

    public void addCompletedAchievement(PlayerStatistics playerStatistics, int achievementId) {
        playerStatistics.getCompletedAchievements().add(achievementId);
    }

    private PlayerStatistics getPlayerStatistics(Long userId) {
        return repository.findById(userId).get();
    }

    private void addChallengeLevelsIfNotExists(int challengeLevel, List<CompletedChallenges> list) {
        if(challengeLevel <= list.size()){
            return ;
        }
        for(int i = list.size(); i < challengeLevel; i++){
            list.add(
                    new CompletedChallenges(challengeLevel)
            );
        }
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
