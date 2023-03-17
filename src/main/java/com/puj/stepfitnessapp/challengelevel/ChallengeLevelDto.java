package com.puj.stepfitnessapp.challengelevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeLevelDto {

    private int dungeonLevel;

    private int amountOfChallenges = 0;

    private int amountOfCompletedChallenges = 0;

    private boolean isLocked = true;

    private int minimalLevelRequirements;
}
