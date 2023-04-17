package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallenge;
import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallengeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_daily_challenges")
public class UserDailyChallenge {

    @Id
    private Long user_id;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Player player;

    @Convert(converter = DailyChallengeConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<DailyChallenge> dailyChallenges;

    private OffsetDateTime dailyChallengeEndDateTime;

    private int amountOfSteps;
}
