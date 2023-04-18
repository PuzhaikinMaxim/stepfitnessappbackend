package com.puj.stepfitnessapp.guildranks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildRanksService {

    private final GuildRankRepository guildRankRepository;

    @Autowired
    public GuildRanksService(GuildRankRepository guildRankRepository) {
        this.guildRankRepository = guildRankRepository;
    }

    public GuildRank getNextGuildRank(GuildRank previousGuildRank) {
        var nextGuildRank = previousGuildRank.getGuildRank() + 1;
        var response = guildRankRepository.findById(nextGuildRank);
        if(response.isEmpty()){
            return previousGuildRank;
        }
        return response.get();
    }
}
