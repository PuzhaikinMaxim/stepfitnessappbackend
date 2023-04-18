package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.guildchallenges.GuildChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildChallengesRewardService {

    private final GuildChallengesRewardRepository guildChallengesRewardRepository;

    @Autowired
    public GuildChallengesRewardService(GuildChallengesRewardRepository guildChallengesRewardRepository) {
        this.guildChallengesRewardRepository = guildChallengesRewardRepository;
    }

    public void generateReward(Guild guild, GuildChallenge guildChallenge) {

    }
}
