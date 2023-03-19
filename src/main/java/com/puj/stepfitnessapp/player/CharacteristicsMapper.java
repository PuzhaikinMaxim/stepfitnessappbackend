package com.puj.stepfitnessapp.player;

public class CharacteristicsMapper {

    public CharacteristicsDto mapPlayerToCharacteristicsDto(Player player) {
        return new CharacteristicsDto(
                player.getStrength(),
                player.getEndurance(),
                player.getUnassignedPoints()
        );
    }
}
