package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallengesConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players_statistics")
public class PlayerStatistics {

    @Id
    private Long player_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "user_id")
    private Player player;

    @Convert(converter = CompletedChallengesConverter.class)
    private List<CompletedChallenges> completedChallenges;

    private int amountOfSteps;

    private int amountOfDuelsWon;
}
