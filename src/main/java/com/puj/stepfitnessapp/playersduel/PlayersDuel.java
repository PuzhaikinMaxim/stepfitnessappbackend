package com.puj.stepfitnessapp.playersduel;

import com.puj.stepfitnessapp.duel.Duel;
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
@Table(name = "players_duel")
public class PlayersDuel {

    @Id
    private Long player_id;

    @OneToOne()
    @JoinColumn(name = "player_id", referencedColumnName = "user_id")
    private Player player;

    @Column(nullable = false)
    private Integer hp;

    @Column(nullable = false)
    private Integer initialHp;

    @Column(nullable = false)
    private Double pointsMultiplier;

    @Column(nullable = false)
    private Integer pointsFixed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "duel_id")
    private Duel duel;


}
