package com.puj.stepfitnessapp.achievement.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AchievementCategoryStepAmount implements AchievementCategory<Integer> {

    private Integer amountOfSteps;

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
