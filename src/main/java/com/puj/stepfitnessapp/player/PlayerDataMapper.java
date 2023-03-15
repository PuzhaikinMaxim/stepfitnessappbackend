package com.puj.stepfitnessapp.player;

public class PlayerDataMapper {

    public PlayerDataDto mapPlayerToPlayerDataDto(Player player) {
        return new PlayerDataDto(
                player.getUser().getUsername(),
                player.getLevel(),
                player.getXp(),
                player.getXpToNextLevel()
        );
    }
}
