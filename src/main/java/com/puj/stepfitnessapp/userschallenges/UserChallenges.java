package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.challenge.Challenge;
import com.puj.stepfitnessapp.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@EnableAutoConfiguration
@Entity
@Table(name = "user_challenges")
public class UserChallenges {
    @EmbeddedId
    private UserChallengesKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @MapsId("challengeId")
    @JoinColumn(referencedColumnName = "challengeId")
    private Challenge challenge;

    private int progress;

    private @NotNull LocalDateTime challengeEndDateTime;

    private int amountOfSteps;

}
