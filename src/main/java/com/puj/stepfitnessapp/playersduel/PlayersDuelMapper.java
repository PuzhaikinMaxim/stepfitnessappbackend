package com.puj.stepfitnessapp.playersduel;

import com.puj.stepfitnessapp.duel.Duel;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerDataDto;

public class PlayersDuelMapper {

    public PlayersDuelDto mapPlayersDuelToPlayersDuelDto(PlayersDuel playersDuel) {
        if(playersDuel == null) return null;

        return new PlayersDuelDto(
                playersDuel.getPlayer().getUser().getUsername(),
                playersDuel.getHp(),
                playersDuel.getInitialHp(),
                playersDuel.getPlayer().getLevel(),
                playersDuel.getPlayer().getImageId()
        );
    }

    public PlayersDuelDto getOpponentsPlayersDuelDto(Duel duel, Player player){
        if(!duel.getWinner().getUser_id().equals(player.getUser_id())) return getLeftPlayer(duel.getWinner());
        if(!duel.getLooser().getUser_id().equals(player.getUser_id())) return getLeftPlayer(duel.getLooser());
        return null;
    }

    private PlayersDuelDto getLeftPlayer(Player opponent) {
        return new PlayersDuelDto(
                opponent.getUser().getUsername(),
                0,
                0,
                opponent.getLevel(),
                opponent.getImageId()
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
