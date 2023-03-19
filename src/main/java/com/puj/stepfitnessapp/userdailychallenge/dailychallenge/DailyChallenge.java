package com.puj.stepfitnessapp.userdailychallenge.dailychallenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyChallenge {

    private int amountOfStepsToComplete;

    private int amountOfXp = 0;

    private boolean completed = false;

    private boolean rewardClaimed = false;

    public DailyChallenge(int amountOfStepsToComplete, int amountOfXp) {
        this.amountOfStepsToComplete = amountOfStepsToComplete;
        this.amountOfXp = amountOfXp;
    }

    public void setIsCompleted(int amountOfSteps) {
        if(amountOfSteps >= amountOfStepsToComplete){
            completed = true;
        }
    }
}
