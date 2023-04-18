package com.puj.stepfitnessapp.guildchallenges;

import com.puj.stepfitnessapp.guild.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildChallengesService {

    private final GuildChallengesRepository guildChallengesRepository;

    @Autowired
    public GuildChallengesService(GuildChallengesRepository guildChallengesRepository) {
        this.guildChallengesRepository = guildChallengesRepository;
    }

    public Boolean updateProgress(int amountOfSteps, Guild guild) {
        var response = guildChallengesRepository
                .findGuildChallengeByGuildChallengeIdAndIsStartedTrue(guild.getGuildId());

        if(response.isEmpty()) return false;

        var guildChallenge = response.get();

        var points = guildChallenge.getAmountOfPointsToFinish();
        var pointsFixed = (amountOfSteps * guildChallenge.getPointsFixed())/100;
        points = points - (int) (pointsFixed*guildChallenge.getPointsMultiplier());
        points = Math.max(0, points);
        guildChallenge.setAmountOfPointsToFinish(points);

        if(points != 0){
            guildChallengesRepository.save(guildChallenge);
            return false;
        }

        return true;
    }

    public void startGuildChallenge(GuildChallenge guildChallenge) {

    }

    public void generateGuildChallenges() {

    }
}
