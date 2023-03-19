package com.puj.stepfitnessapp.userdailychallenge.dailychallenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyChallengeDto {

    private int amountOfSteps;

    private boolean isCompleted;
}
