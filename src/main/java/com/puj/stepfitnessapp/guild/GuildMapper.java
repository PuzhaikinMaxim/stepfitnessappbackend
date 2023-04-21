package com.puj.stepfitnessapp.guild;

public class GuildMapper {

    public GuildDto mapToGuildDto(Guild guild) {
        return new GuildDto(
                guild.getGuildId(),
                guild.getAmountOfCompletedChallenges(),
                guild.getXp(),
                guild.getGuildName()
        );
    }

}
