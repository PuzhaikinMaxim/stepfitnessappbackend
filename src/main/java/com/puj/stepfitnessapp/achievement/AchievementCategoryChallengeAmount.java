package com.puj.stepfitnessapp.achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AchievementCategoryChallengeAmount implements AchievementCategory<Integer> {

    private final Integer amountOfChallenges;

    @Override
    public String getDescription() {
        return "Пройдите " + amountOfChallenges + " испытаний";
    }

    @Override
    public Integer getComparableValue() {
        return amountOfChallenges;
    }

    @Override
    public Boolean isCompleted(Integer value) {
        return value >= amountOfChallenges;
    }
}
