package com.puj.stepfitnessapp.achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AchievementCategoryStepAmount implements AchievementCategory<Integer> {

    private final Integer amountOfSteps;

    @Override
    public String getDescription() {
        return "Пройдите " + amountOfSteps + " шагов";
    }

    @Override
    public Integer getComparableValue() {
        return amountOfSteps;
    }

    @Override
    public Boolean isCompleted(Integer value) {
        return value >= amountOfSteps;
    }
}
