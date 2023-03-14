package com.puj.stepfitnessapp.userdailychallenge.dailychallenge;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyChallenge {

    private int amountOfStepsToComplete;

    private boolean isCompleted = false;

    public void setIsCompleted(int amountOfSteps) {
        if(amountOfSteps >= amountOfStepsToComplete){
            isCompleted = true;
        }
    }
}
