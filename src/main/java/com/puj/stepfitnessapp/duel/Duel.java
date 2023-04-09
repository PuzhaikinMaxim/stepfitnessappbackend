package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "duels")
public class Duel {

    @SequenceGenerator(
            name = "duel_sequence",
            sequenceName = "duel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "duel_sequence"
    )
    @Id
    private Long duelId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_player_id", referencedColumnName = "user_id", unique = true)
    private Player firstPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_player_id", referencedColumnName = "user_id", unique = true)
    private Player secondPlayer;

    @Column(nullable = false)
    private Integer firstPlayerHp;

    @Column(nullable = false)
    private Integer secondPlayerHp;

    @Column(nullable = false)
    private Integer firstPlayerPointsMultiplier;

    @Column(nullable = false)
    private Integer secondPlayerPointsMultiplier;

    @Column(nullable = false)
    private Integer firstPlayerPointsFixed;

    @Column(nullable = false)
    private Integer secondPlayerPointsFixed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "winner_id", referencedColumnName = "user_id", unique = true)
    private Player winner;
}
