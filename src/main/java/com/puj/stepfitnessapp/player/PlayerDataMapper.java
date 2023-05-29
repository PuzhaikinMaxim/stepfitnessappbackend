package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.playerstatistics.PlayerStatistics;

public class PlayerDataMapper {

    public PlayerDataDto mapPlayerToPlayerDataDto(Player player) {
        return new PlayerDataDto(
                player.getUser().getUsername(),
                player.getImageId(),
                player.getLevel(),
                player.getXp(),
                player.getXpToNextLevel()
        );
    }

    public PlayerProfileData mapToPlayerProfileData(Player player, PlayerStatistics playerStatistics) {
        var guild = player.getGuild();
        String guildName = null;
        Integer guildLogoId = null;
        if(guild != null){
            guildName = guild.getGuildName();
            guildLogoId = guild.getGuildLogoId();
        }
        return new PlayerProfileData(
                mapPlayerToPlayerDataDto(player),
                playerStatistics.getCompletedChallenges().size(),
                playerStatistics.getCompletedAchievements().size(),
                playerStatistics.getAmountOfSteps(),
                playerStatistics.getAmountOfDuelsWon(),
                guildName,
                guildLogoId
        );
    }
}
