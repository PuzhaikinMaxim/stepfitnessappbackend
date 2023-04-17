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

    private PlayersDuelDto player;

    private PlayersDuelDto opponent;

    private Boolean isDuelFinished;

    private Boolean isWon;

    private Boolean isCancelled;
}
