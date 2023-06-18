package com.puj.stepfitnessapp.guildenterrequest;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.guild.GuildService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GuildEnterRequestService {

    private final GuildEnterRequestRepository guildEnterRequestRepository;

    private final PlayerService playerService;

    private final GuildService guildService;

    @Autowired
    public GuildEnterRequestService(
            GuildEnterRequestRepository guildEnterRequestRepository,
            PlayerService playerService,
            GuildService guildService
    ) {
        this.guildEnterRequestRepository = guildEnterRequestRepository;
        this.playerService = playerService;
        this.guildService = guildService;
    }

    public GuildEnterRequest getGuildEnterRequestById(Long guildEnterRequest) {
        return guildEnterRequestRepository.findById(guildEnterRequest).orElse(null);
    }

    public GuildEnterRequest getGuildEnterRequestByGuildAndPlayer(Long userId, Long guildId) {
        var guild = guildService.findGuildById(guildId);
        var player = playerService.getPlayerById(userId);
        return guildEnterRequestRepository.getGuildEnterRequestByGuildAndPlayer(guild, player).orElse(null);
    }

    public List<GuildEnterRequest> getGuildEnterRequests(Long userId) {
        var guild = guildService.findGuildByUserId(userId);
        if(!guild.getOwner().getUser_id().equals(userId)) return null;
        return guild.getGuildEnterRequests();
    }

    public void sendGuildEnterRequest(Long userId, Long guildId) {
        var guild = guildService.findGuildById(guildId);
        var player = playerService.getPlayerById(userId);
        if(player.getUser_id().equals(guild.getOwner().getUser_id())) return;
        if(getGuildEnterRequestByGuildAndPlayer(userId, guildId) != null) return;
        addNewGuildEnterRequest(player, guild);
    }

    public void acceptGuildRequest(Long requestId, Long userId) {
        var guildEnterRequest = guildEnterRequestRepository.findById(requestId).get();
        var guild = guildEnterRequest.getGuild();
        if(!guild.getOwner().getUser_id().equals(userId)) return;

        playerService.assignToGuild(guildEnterRequest.getPlayer(), guild);
        guildEnterRequestRepository.deleteByPlayer(guildEnterRequest.getPlayer());
    }

    public void addNewGuildEnterRequest(Player player, Guild guild) {
        var guildEnterRequest = new GuildEnterRequest(guild, player);
        guildEnterRequestRepository.save(guildEnterRequest);
    }

    public void cancelGuildEnterRequest(GuildEnterRequest guildEnterRequest) {
        guildEnterRequestRepository.delete(guildEnterRequest);
    }

    public Map<Long, GuildEnterRequest> getPlayerEnterRequests(Player player) {
        var guildEnterRequests
                = guildEnterRequestRepository.getGuildEnterRequestsByPlayer(player);
        if(guildEnterRequests.isEmpty()) return null;
        var guildEnterRequestsMap = new HashMap<Long, GuildEnterRequest>();
        for(GuildEnterRequest guildEnterRequest : guildEnterRequests.get()){
            if(guildEnterRequestsMap.getOrDefault(guildEnterRequest.getGuild().getGuildId(), null) == null){
                guildEnterRequestsMap.put(guildEnterRequest.getGuild().getGuildId(), guildEnterRequest);
            }
        }
        return guildEnterRequestsMap;
    }
}
