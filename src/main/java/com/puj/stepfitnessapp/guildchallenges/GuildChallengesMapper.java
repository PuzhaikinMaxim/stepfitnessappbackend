package com.puj.stepfitnessapp.guildchallenges;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class GuildChallengesMapper {

    public CurrentGuildChallengeDto mapToCurrentGuildChallengeDto(GuildChallenge guildChallenge) {
        if(guildChallenge == null) return null;
        return new CurrentGuildChallengeDto(
                guildChallenge.getProgress(),
                guildChallenge.getAmountOfPointsToFinish(),
                getTimeTillEnd(guildChallenge.getChallengeEndDateTime())
        );
    }

    public List<GuildChallengeDto> mapToGuildChallengeDtoList(List<GuildChallenge> guildChallengeList) {
        ArrayList<GuildChallengeDto> guildChallengeDtoList = new ArrayList<>();
        for(GuildChallenge guildChallenge : guildChallengeList){
            guildChallengeDtoList.add(
                    new GuildChallengeDto(
                            guildChallenge.getGuildChallengeId(),
                            guildChallenge.getAmountOfPointsToFinish(),
                            guildChallenge.getBaseHoursToFinish()
                    )
            );
        }
        return guildChallengeDtoList;
    }

    private String getTimeTillEnd(LocalDateTime endDateTime) {
        var timeNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        var endTime = endDateTime.toEpochSecond(ZoneOffset.UTC);
        var res = Math.max(0, endTime - timeNow);
        res = res / 60;
        var minutes = res % 60;
        var hours = res / 60;
        return hours == 0 ? minutes + " м" : hours + " ч " + minutes + " м";
    }
}
