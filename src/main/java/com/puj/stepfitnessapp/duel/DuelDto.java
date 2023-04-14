package com.puj.stepfitnessapp.duel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DuelDto {

    private int playerHp;

    private int playerInitialHp;

    private int opponentHp;

    private int opponentInitialHp;

    private String playerName;

    private String opponentName;

    private Boolean isDuelFinished;

    private Boolean isWon;
}
