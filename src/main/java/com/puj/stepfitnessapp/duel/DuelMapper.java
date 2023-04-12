package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.player.Player;

public class DuelMapper {

    public Duel mapToDuel(Player firstPlayer, Player secondPlayer) {
        return new Duel(
                firstPlayer,
                secondPlayer,
                firstPlayer.calculateHp(10000),
                secondPlayer.calculateHp(10000),
                firstPlayer.calculatePointMultiplier(),
                secondPlayer.calculatePointMultiplier(),
                firstPlayer.calculateAmountOfAdditionalPoints(),
                secondPlayer.calculateAmountOfAdditionalPoints(),
                null
        );
    }
}
