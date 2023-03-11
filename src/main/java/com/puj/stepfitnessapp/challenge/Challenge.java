package com.puj.stepfitnessapp.challenge;

import com.puj.stepfitnessapp.challengelevel.ChallengeLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
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

    @NotBlank
    @Column(unique = true)
    private String challengeName;

    @NotNull
    private int amountOfPoints;

    @NotNull
    private int baseHoursToFinish;

    @NotNull
    private int minimumUserLevel;

    /*
    private @NotBlank int challengeLevel;

     */


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_level", referencedColumnName = "challengeLevel")
    private ChallengeLevel level;
}
