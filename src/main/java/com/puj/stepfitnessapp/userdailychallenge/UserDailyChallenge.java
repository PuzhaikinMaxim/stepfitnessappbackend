package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallenge;
import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallengeConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "user_daily_challenges")
public class UserDailyChallenge {

    @Id
    private Long user_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Player player;

    @Convert(converter = DailyChallengeConverter.class)
    private List<DailyChallenge> dailyChallenges;

    private int amountOfSteps;
}
