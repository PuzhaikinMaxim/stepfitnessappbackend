package com.puj.stepfitnessapp.achievement.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AchievementCategoryChallengeAmount implements AchievementCategory<Integer> {

    private Integer amountOfChallenges;

    @Override
    @JsonIgnore
    public String getDescription() {
        return "Пройдите " + amountOfChallenges + " испытаний";
    }

    @Override
    @JsonIgnore
    public Integer getComparableValue() {
        return amountOfChallenges;
    }

    @Override
    @JsonIgnore
    public Boolean isCompleted(Integer value) {
        return value >= amountOfChallenges;
    }

    @Override
    @JsonIgnore
    public Integer getType() {
        return 2;
    }
}
