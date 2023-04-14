package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "first_player_id", referencedColumnName = "user_id", unique = true)
    private Player firstPlayer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "second_player_id", referencedColumnName = "user_id", unique = true)
    private Player secondPlayer;

    @Column(nullable = false)
    private Integer firstPlayerHp;

    @Column(nullable = false)
    private Integer secondPlayerHp;

    @Column(nullable = false)
    private Integer firstPlayerInitialHp;

    @Column(nullable = false)
    private Integer secondPlayerInitialHp;

    @Column(nullable = false)
    private Double firstPlayerPointsMultiplier;

    @Column(nullable = false)
    private Double secondPlayerPointsMultiplier;

    @Column(nullable = false)
    private Integer firstPlayerPointsFixed;

    @Column(nullable = false)
    private Integer secondPlayerPointsFixed;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "winner_id", referencedColumnName = "user_id", unique = true)
    private Player winner;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cancel_duel_id", referencedColumnName = "user_id", unique = true)
    private Player cancelDuelPlayer;

    public Duel(Player firstPlayer,
                Player secondPlayer,
                Integer firstPlayerHp,
                Integer secondPlayerHp,
                Double firstPlayerPointsMultiplier,
                Double secondPlayerPointsMultiplier,
                Integer firstPlayerPointsFixed,
                Integer secondPlayerPointsFixed,
                Player winner
    ) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.firstPlayerHp = firstPlayerHp;
        this.secondPlayerHp = secondPlayerHp;
        this.firstPlayerInitialHp = firstPlayerHp;
        this.secondPlayerInitialHp = secondPlayerHp;
        this.firstPlayerPointsMultiplier = firstPlayerPointsMultiplier;
        this.secondPlayerPointsMultiplier = secondPlayerPointsMultiplier;
        this.firstPlayerPointsFixed = firstPlayerPointsFixed;
        this.secondPlayerPointsFixed = secondPlayerPointsFixed;
        this.winner = winner;
    }
}
