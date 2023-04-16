package com.puj.stepfitnessapp.playersduel;

import com.puj.stepfitnessapp.duel.Duel;
import com.puj.stepfitnessapp.player.Player;

public class PlayersDuelMapper {

    public PlayersDuelDto mapPlayersDuelToPlayersDuelDto(PlayersDuel playersDuel) {
        if(playersDuel == null) return null;

        return new PlayersDuelDto(
                playersDuel.getPlayer().getUser().getUsername(),
                playersDuel.getHp(),
                playersDuel.getInitialHp()
        );
    }

    public PlayersDuel mapToPlayersDuel(Player player, Duel duel) {

        var hp = player.calculateHp(10000);

        return new PlayersDuel(
                player.getUser_id(),
                player,
                hp,
                hp,
                player.calculatePointMultiplier(),
                player.calculateAmountOfAdditionalPoints(),
                duel
        );
    }
}
