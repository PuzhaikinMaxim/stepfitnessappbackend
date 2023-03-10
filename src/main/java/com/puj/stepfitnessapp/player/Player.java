package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    private Long user_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @NotNull
    private int level;

    @NotNull
    private int xp;

    @NotNull
    private int xpToNextLevel;

    @NotNull
    private int endurance;

    @NotNull
    private int strength;

    @NotNull
    private int amountOfSteps;

    public Player(
            Long user_id,
            User user,
            @NotNull int level,
            @NotNull int xp,
            @NotNull int xpToNextLevel,
            @NotNull int endurance,
            @NotNull int strength,
            @NotNull int amountOfSteps
    ) {
        this.user_id = user_id;
        this.user = user;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        this.endurance = endurance;
        this.strength = strength;
        this.amountOfSteps = amountOfSteps;
    }
}
