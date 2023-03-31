package com.puj.stepfitnessapp.achievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduledAchievementList {

    private AchievementRepository repository;

    private final static int FIVE_MINUTE_DELAY = 1000 * 60 * 5;

    private Map<String, List<Achievement>> achievementGroupByType;

    @Autowired
    public ScheduledAchievementList(AchievementRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = FIVE_MINUTE_DELAY)
    public void updateItemList() {
        achievementGroupByType = repository.findAll().stream().collect(Collectors.groupingBy(
                s -> s.getAchievementCategory().getClass().getSimpleName()
        ));
    }

    public List<Achievement> getStepAmountAchievementList() {
        return achievementGroupByType.get(AchievementCategoryStepAmount.class.getSimpleName());
    }

    public List<Achievement> getDuelAmountAchievementList() {
        return achievementGroupByType.get(AchievementCategoryDuelAmount.class.getSimpleName());
    }

    public List<Achievement> getChallengeAmountAchievementList() {
        return achievementGroupByType.get(AchievementCategoryChallengeAmount.class.getSimpleName());
    }
}
