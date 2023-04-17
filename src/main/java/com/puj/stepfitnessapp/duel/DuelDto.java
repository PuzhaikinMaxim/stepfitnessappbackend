package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.playersduel.PlayersDuelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DuelDto {

    /*
    private int playerHp;

    private int playerInitialHp;

    private int opponentHp;

    private int opponentInitialHp;

     */

    private PlayersDuelDto player;

    private PlayersDuelDto opponent;

    /*
    private String playerName;

    private String opponentName;

     */

    private Boolean isDuelFinished;

    private Boolean isWon;

    private Boolean isCancelled;
}
