package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.challenge.Challenge;
import com.puj.stepfitnessapp.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
//@Entity
public class UserChallenges {

    /*

    @EmbeddedId
    private UserChallengesKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @MapsId("challengeId")
    @JoinColumn(referencedColumnName = "challenge_id")
    private Challenge challenge;

    private int progress;

    @Column(name = "time_left")
    private int timeLeft;

    @Column(name = "amount_of_steps")
    private int amountOfSteps;

     */
}
