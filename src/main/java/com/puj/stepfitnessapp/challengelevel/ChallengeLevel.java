package com.puj.stepfitnessapp.challengelevel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "challenge_levels")
public class ChallengeLevel {

    @Id
    private int challengeLevel;
}
