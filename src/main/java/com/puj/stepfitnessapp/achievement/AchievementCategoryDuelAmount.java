package com.puj.stepfitnessapp.achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AchievementCategoryDuelAmount implements AchievementCategory<Integer> {

    private final Integer amountOfDuels;

    @Override
    public String getDescription() {
        var description = "Победите в " + amountOfDuels + " дуэлях";
        if(amountOfDuels == 1){
            description = "Победите в 1 дуэли";
        }
        return description;
    }

    @Override
    public Integer getComparableValue() {
        return amountOfDuels;
    }

    @Override
    public Boolean isCompleted(Integer value) {
        return value >= amountOfDuels;
    }
}
