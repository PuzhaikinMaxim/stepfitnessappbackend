package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.challenge.Challenge;
import com.puj.stepfitnessapp.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@EnableAutoConfiguration
@NoArgsConstructor
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

    private @NotNull int amountOfSteps;

    private @NotNull boolean isCompleted = false;

    private @NotNull boolean isFailed = false;

    public UserChallenges(UserChallengesKey id, User user, Challenge challenge, int progress, @NotNull LocalDateTime challengeEndDateTime, int amountOfSteps) {
        this.id = id;
        this.user = user;
        this.challenge = challenge;
        this.progress = progress;
        this.challengeEndDateTime = challengeEndDateTime;
        this.amountOfSteps = amountOfSteps;
    }
}
