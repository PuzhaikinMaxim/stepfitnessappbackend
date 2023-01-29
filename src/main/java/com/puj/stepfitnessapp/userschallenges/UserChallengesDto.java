package com.puj.stepfitnessapp.userschallenges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserChallengesDto {

    private long challengeId;

    private String challengeName;

    private int amountOfPoints;

    private String challengeEndDateTime;

    private int progress;

    private int amountOfSteps;

    private int challengeLevel;
}
