package com.puj.stepfitnessapp.achievement;

import com.puj.stepfitnessapp.playerstatistics.PlayerStatistics;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    private final PlayerStatisticsService playerStatisticsService;

    private final ScheduledAchievementList scheduledAchievementList;

    @Autowired
    public AchievementService(
            AchievementRepository achievementRepository,
            PlayerStatisticsService playerStatisticsService,
            ScheduledAchievementList scheduledAchievementList
    ) {
        this.achievementRepository = achievementRepository;
        this.playerStatisticsService = playerStatisticsService;
        this.scheduledAchievementList = scheduledAchievementList;
    }

    public void setCompletedChallenges(PlayerStatistics playerStatistics, int amountOfSteps) {
        var achievements = scheduledAchievementList.getStepAmountAchievementList();

        for(Achievement achievement: achievements){
            var achievementCategory = (AchievementCategoryStepAmount) achievement.getAchievementCategory();

            if(achievementCategory.getComparableValue() > amountOfSteps) break;

            if(achievementCategory.getComparableValue() <= playerStatistics.getAmountOfSteps()) continue;
        }
    }
}
