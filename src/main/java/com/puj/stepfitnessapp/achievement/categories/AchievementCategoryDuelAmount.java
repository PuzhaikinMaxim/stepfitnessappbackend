package com.puj.stepfitnessapp.achievement.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AchievementCategoryDuelAmount implements AchievementCategory<Integer> {

    private final Integer amountOfDuels;

    @Override
    @JsonIgnore
    public String getDescription() {
        var description = "Победите в " + amountOfDuels + " дуэлях";
        if(amountOfDuels == 1){
            description = "Победите в 1 дуэли";
        }
        return description;
    }

    @Override
    @JsonIgnore
    public Integer getComparableValue() {
        return amountOfDuels;
    }

    @Override
    @JsonIgnore
    public Boolean isCompleted(Integer value) {
        return value >= amountOfDuels;
    }

    @Override
    @JsonIgnore
    public Integer getType() {
        return 3;
    }
}
