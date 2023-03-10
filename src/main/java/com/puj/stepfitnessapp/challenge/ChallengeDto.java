package com.puj.stepfitnessapp.challenge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeDto {

    private int id;

    private String challengeName;

    private int pointsGained;

    private int goal;

    private int stepsTaken;

    private String timeTillEnd;

    public ChallengeDto(int id, String challengeName, int pointsGained, int goal, int stepsTaken, String timeTillEnd) {
        this.id = id;
        this.challengeName = challengeName;
        this.pointsGained = pointsGained;
        this.goal = goal;
        this.stepsTaken = stepsTaken;
        this.timeTillEnd = timeTillEnd;
    }
}
