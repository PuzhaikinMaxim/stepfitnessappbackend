package com.puj.stepfitnessapp.guildchallenges;

import com.puj.stepfitnessapp.guild.Guild;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "guild_challenges")
public class GuildChallenge {

    @Id
    @SequenceGenerator(
            name = "guild_challenge_sequence",
            sequenceName = "guild_challenge_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guild_challenge_sequence"
    )
    private Long guildChallengeId;

    @Column(nullable = false)
    private Integer xp;

    private Integer baseHoursToFinish;

    private Integer amountOfPointsToFinish;

    private LocalDateTime challengeEndDateTime;

    private Integer pointsFixed;

    private Double pointsMultiplier;

    private Integer progress;

    @ManyToOne
    @JoinColumn(name = "guild")
    private Guild guild;

    @Column(columnDefinition = "double precision default 1.0")
    private Double difficultyRewardMultiplier;

    @Column(nullable = false)
    private Boolean isStarted = false;


    public GuildChallenge(
            Integer xp,
            Integer baseHoursToFinish,
            Integer amountOfPointsToFinish,
            Guild guild,
            Double difficultyRewardMultiplier
    ) {
        this.xp = xp;
        this.baseHoursToFinish = baseHoursToFinish;
        this.amountOfPointsToFinish = amountOfPointsToFinish;
        this.guild = guild;
        this.difficultyRewardMultiplier = difficultyRewardMultiplier;
    }
}
