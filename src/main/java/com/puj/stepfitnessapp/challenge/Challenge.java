package com.puj.stepfitnessapp.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "challenges")
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
    private @NotBlank @Column(unique = true) String challengeName;

    private @NotBlank int amountOfPoints;
    private @NotBlank int baseHoursToFinish;
    private @NotBlank int minimumUserLevel;
    private @NotBlank int challengeLevel;

    public Challenge() {

    }

    public Challenge(
            int amountOfPoints,
            int baseHoursToFinish
    ) {
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
