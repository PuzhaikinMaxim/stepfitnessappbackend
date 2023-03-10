package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.player.Player;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "players_statistics")
public class PlayerStatistics {

    @Id
    private Long player_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "user_id")
    private Player player;

    private String completedChallenges;

    private int amountOfSteps;

    private int amountOfDuelsWon;
}
