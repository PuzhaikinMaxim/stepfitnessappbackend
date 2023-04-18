package com.puj.stepfitnessapp.challenge;

import com.puj.stepfitnessapp.challengelevel.ChallengeLevel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
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
    @Column(unique = true, length = 30)
    private String challengeName;

    @NotNull
    private int amountOfPoints;

    @NotNull
    private int baseHoursToFinish;

    @NotNull
    private int minimumUserLevel;

    @NotNull
    private int amountOfXp;

    @ManyToOne()
    @JoinColumn(name = "challenge_level", referencedColumnName = "challengeLevel")
    private ChallengeLevel level;
}
