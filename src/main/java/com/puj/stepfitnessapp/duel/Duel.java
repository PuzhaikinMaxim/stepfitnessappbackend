package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.playersduel.PlayersDuel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "duel_id")
    private List<PlayersDuel> playersDuel = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "winner_id", referencedColumnName = "user_id")
    private Player winner;

    @OneToOne()
    @JoinColumn(name = "looser_id", referencedColumnName = "user_id")
    private Player looser;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDuelCancelled;
}
