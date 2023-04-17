package com.puj.stepfitnessapp.achievement;

import com.puj.stepfitnessapp.achievement.categories.AchievementCategoryStepAmount;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatistics;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    private final PlayerStatisticsService playerStatisticsService;

    private final ScheduledAchievementList scheduledAchievementList;

    @Autowired
    public AchievementService(
            AchievementRepository achievementRepository,
            @Lazy PlayerStatisticsService playerStatisticsService,
            ScheduledAchievementList scheduledAchievementList
    ) {
        this.achievementRepository = achievementRepository;
        this.playerStatisticsService = playerStatisticsService;
        this.scheduledAchievementList = scheduledAchievementList;
        achievementRepository.saveAll(getAchievements());
    }

    private List<Achievement> getAchievements() {
        ArrayList achievementList = new ArrayList<Achievement>();
        achievementList.add(new Achievement(
                1,
                "Новичок",
                new AchievementCategoryStepAmount(10000)
        ));
        return achievementList;
    }

    public List<Achievement> getAchievementList() {
        return achievementRepository.findAll();
    }

    public void addStepCountCompletedAchievements(
            PlayerStatistics playerStatistics,
            int newAmountOfSteps,
            int oldAmountOfSteps) {
        var achievements = scheduledAchievementList.getStepAmountAchievementList();

        addAmountAchievements(playerStatistics, achievements, newAmountOfSteps, oldAmountOfSteps);
    }

    public void addChallengesAmountAchievements(
            PlayerStatistics playerStatistics,
            int newAmountOfChallenges,
            int oldAmountOfChallenges) {
        var achievements = scheduledAchievementList.getChallengeAmountAchievementList();

        addAmountAchievements(playerStatistics, achievements, newAmountOfChallenges, oldAmountOfChallenges);
    }

    public void addDuelAmountAchievements(
            PlayerStatistics playerStatistics,
            int newAmountOfDuels,
            int oldAmountOfDuels) {
        var achievements = scheduledAchievementList.getDuelAmountAchievementList();

        addAmountAchievements(playerStatistics, achievements, newAmountOfDuels, oldAmountOfDuels);
    }

    private<T> void addAmountAchievements(
            PlayerStatistics playerStatistics,
            List<Achievement> achievements,
            int newAmount,
            int oldAmount
    ) {
        if(achievements == null) return;
        for(Achievement achievement: achievements){
            var achievementCategory = (AchievementCategoryStepAmount) achievement.getAchievementCategory();

            if(achievementCategory.getComparableValue() > newAmount) break;

            if(achievementCategory.getComparableValue() <= oldAmount) continue;

            playerStatisticsService.addCompletedAchievement(playerStatistics, achievement.getAchievementId());
        }
    }
}
