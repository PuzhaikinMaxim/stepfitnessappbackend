package com.puj.stepfitnessapp.guild;

import com.puj.stepfitnessapp.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GuildMapper {

    public GuildDto mapToGuildDto(Guild guild) {
        return new GuildDto(
                guild.getGuildId(),
                guild.getAmountOfCompletedChallenges(),
                guild.getXp(),
                guild.getGuildName()
        );
    }

    public List<GuildListItemDto> mapToGuildListItemDto(List<Guild> guildList, Player player) {
        ArrayList<GuildListItemDto> guildListItemDtoList = new ArrayList<>();
        for(Guild guild : guildList){
            if(player.getGuild() != null && player.getGuild().getGuildId().equals(guild.getGuildId())){
                continue;
            }
            guildListItemDtoList.add(
                    new GuildListItemDto(
                            guild.getGuildId(),
                            guild.getGuildName(),
                            guild.getGuildRank().getGuildRank(),
                            guild.getPlayers().size()
                    )
            );
        }
        return guildListItemDtoList;
    }

    public GuildStatisticsDto mapToGuildStatisticsDto(Guild guild) {
        var guildParticipants = guild.getPlayers();
        var collectiveLevel = guildParticipants.stream().mapToInt(Player::getLevel).sum();
        return new GuildStatisticsDto(
                guild.getAmountOfCompletedChallenges(),
                guildParticipants.size(),
                collectiveLevel
        );
    }

    public List<GuildParticipantDto> mapToGuildParticipantDto(Guild guild, Long userId) {
        ArrayList<GuildParticipantDto> guildParticipantDtoList = new ArrayList<>();
        var guildParticipants = guild.getPlayers();
        for(Player player : guildParticipants){
            if(userId.equals(player.getUser_id())) continue;
            guildParticipantDtoList.add(
                    new GuildParticipantDto(
                            player.getUser_id(),
                            player.getUser().getUsername(),
                            player.getImageId(),
                            player.getLevel()
                    )
            );
        }
        return guildParticipantDtoList;
    }

    public GuildDataDto mapToGuildDataDto(Guild guild) {
        return new GuildDataDto(
                guild.getGuildName(),
                guild.getGuildRank().getGuildRank(),
                guild.getGuildLogoId()
        );
    }

    public GuildEditionInfo mapToGuildEditionInfo(Guild guild) {
        return new GuildEditionInfo(
                guild.getGuildName(),
                guild.getGuildLogoId()
        );
    }
}
