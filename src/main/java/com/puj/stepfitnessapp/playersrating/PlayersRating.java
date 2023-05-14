package com.puj.stepfitnessapp.playersrating;

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
@Table(name = "players_rating")
public class PlayersRating {

    @Id
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rating_sequence"
    )
    private Long ratingId;

    @OneToOne()
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(columnDefinition = "int default 0", nullable = false)
    private Integer amountOfSteps = 0;

    @Column(columnDefinition = "int default 0", nullable = false)
    private Integer amountOfDuelsWon = 0;

    public PlayersRating(Player player, Integer amountOfSteps, Integer amountOfDuelsWon) {
        this.player = player;
        this.amountOfSteps = amountOfSteps;
        this.amountOfDuelsWon = amountOfDuelsWon;
    }
}
