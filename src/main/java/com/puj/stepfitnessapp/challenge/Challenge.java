package com.puj.stepfitnessapp.challenge;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Challenge {

    @Id
    @SequenceGenerator(
            name = "challenge_sequence",
            sequenceName = "challenge_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "challenge_sequence"
    )
    private Long challengeId;

    private int amountOfPoints;
    private int baseHoursToFinish;

    public Challenge() {

    }

    public Challenge(int amountOfPoints, int baseHoursToFinish) {
        this.amountOfPoints = amountOfPoints;
        this.baseHoursToFinish = baseHoursToFinish;
    }

    public void setChallengeId(Long id) {
        this.challengeId = id;
    }

    @Id
    public Long getChallengeId() {
        return challengeId;
    }
}
