package com.puj.stepfitnessapp.achievement.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AchievementCategoryStepAmount implements AchievementCategory<Integer> {

    private final Integer amountOfSteps;

    @Override
    @JsonIgnore
    public String getDescription() {
        return "Пройдите " + amountOfSteps + " шагов";
    }

    @Override
    @JsonIgnore
    public Integer getComparableValue() {
        return amountOfSteps;
    }

    @Override
    @JsonIgnore
    public Boolean isCompleted(Integer value) {
        return value >= amountOfSteps;
    }

    @Override
    @JsonIgnore
    public Integer getType() {
        return 1;
    }
}
