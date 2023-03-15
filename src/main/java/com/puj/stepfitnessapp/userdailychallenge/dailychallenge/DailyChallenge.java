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

    private boolean isCompleted = false;

    public DailyChallenge(int amountOfStepsToComplete) {
        this.amountOfStepsToComplete = amountOfStepsToComplete;
    }

    public void setIsCompleted(int amountOfSteps) {
        if(amountOfSteps >= amountOfStepsToComplete){
            isCompleted = true;
        }
    }
}
