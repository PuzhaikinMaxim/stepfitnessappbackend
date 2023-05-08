package com.puj.stepfitnessapp.guild;

import com.puj.stepfitnessapp.guildrank.GuildRanksService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    private final GuildRepository guildRepository;

    private final PlayerService playerService;

    private final GuildRanksService guildRanksService;

    @Autowired
    public GuildService(
            GuildRepository guildRepository,
            PlayerService playerService,
            GuildRanksService guildRanksService
    ) {
        this.guildRepository = guildRepository;
        this.playerService = playerService;
        this.guildRanksService = guildRanksService;
    }

    public void incrementAmountOfCompletedChallenges(Guild guild) {
        var amountOfCompletedChallenges = guild.getAmountOfCompletedChallenges() + 1;
        guild.setAmountOfCompletedChallenges(amountOfCompletedChallenges);
        guildRepository.save(guild);
    }

    public void createGuild(Long userId, String guildName, Integer guildLogoId) {
        var player = playerService.getPlayerById(userId);
        var guildRank = guildRanksService.getFirstGuildRank();
        var guild = new Guild(player,guildRank,guildName,guildLogoId);
        guildRepository.save(guild);
        playerService.assignToGuild(player, guild);
    }

    public void expelPlayer(Long userId, Long expelledPlayerId) {
        var guild = findGuildByUserId(userId);
        if(guild == null) return;
        if(!guild.getOwner().getUser_id().equals(userId)) return;
        var player = playerService.getPlayerById(expelledPlayerId);
        playerService.unassignPlayerFromGuild(player);
    }

    public void leaveGuild(Long userId) {
        var guild = findGuildByUserId(userId);
        if(guild == null || guild.getOwner().getUser_id().equals(userId)) return;
        var player = playerService.getPlayerById(userId);
        playerService.unassignPlayerFromGuild(player);
    }

    public Guild findGuildByUserId(Long userId) {
        return guildRepository.getGuildByUserId(userId).orElse(null);
    }

    public Guild findGuildById(Long guildId) {
        var guild = guildRepository.findById(guildId);
        return guildRepository.findById(guildId).get();
    }

    public List<Guild> getGuildList() {
        return guildRepository.findAll();
    }

    public Guild getGuild(Long userId) {
        return guildRepository.getGuildByUserId(userId).orElse(null);
    }

    public List<Player> getGuildParticipants(Long userId) {
        var guild = guildRepository.getGuildByUserId(userId).orElse(null);
        if(guild == null) return null;
        return guild.getPlayers();
    }

    public Boolean getIsOwner(Long userId) {
        var guild = guildRepository.getGuildByUserId(userId).orElse(null);
        if(guild == null) return false;
        return guild.getOwner().getUser_id().equals(userId);
    }
}
