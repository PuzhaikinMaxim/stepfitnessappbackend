package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.playerstatistics.PlayerStatistics;

public class PlayerDataMapper {

    public PlayerDataDto mapPlayerToPlayerDataDto(Player player) {
        return new PlayerDataDto(
                player.getUser().getUsername(),
                player.getLevel(),
                player.getXp(),
                player.getXpToNextLevel()
        );
    }

    public PlayerProfileData mapToPlayerProfileData(Player player, PlayerStatistics playerStatistics) {
        return new PlayerProfileData(
                mapPlayerToPlayerDataDto(player),
                playerStatistics.getCompletedChallenges().size(),
                playerStatistics.getCompletedAchievements().size(),
                playerStatistics.getAmountOfSteps(),
                playerStatistics.getAmountOfDuelsWon(),
                player.getGuild().getGuildName(),
                player.getGuild().getGuildLogoId()
        );
    }
}
