package com.puj.stepfitnessapp.guildenterrequest;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.player.Player;
import org.springframework.stereotype.Service;

@Service
public class GuildEnterRequestService {

    private final GuildEnterRequestRepository guildEnterRequestRepository;

    public GuildEnterRequestService(GuildEnterRequestRepository guildEnterRequestRepository) {
        this.guildEnterRequestRepository = guildEnterRequestRepository;
    }

    public void addNewGuildRequest(Player player, Guild guild) {
        var guildEnterRequest = new GuildEnterRequest(guild, player);
        guildEnterRequestRepository.save(guildEnterRequest);
    }

    public void cancelGuildRequest(GuildEnterRequest guildEnterRequest) {
        guildEnterRequestRepository.delete(guildEnterRequest);
    }
}
