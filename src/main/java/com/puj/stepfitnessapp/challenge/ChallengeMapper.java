package com.puj.stepfitnessapp.challenge;

import java.util.ArrayList;
import java.util.List;

public class ChallengeMapper {

    public ChallengeDto mapChallengeToChallengeDto(Challenge challenge) {

        return new ChallengeDto(
                challenge.getChallengeId().intValue(),
                challenge.getChallengeName(),
                0,
                challenge.getAmountOfPoints(),
                0,
                challenge.getBaseHoursToFinish() + "ч"
        );
    }

    public List<ChallengeDto> mapChallengeListToChallengeDtoList(List<Challenge> challengeList) {
        if(challengeList == null) return null;

        final var challengeDtoList = new ArrayList<ChallengeDto>();
        for(Challenge challenge : challengeList){
            final var challengeDtoItem = mapChallengeToChallengeDto(challenge);
            challengeDtoList.add(challengeDtoItem);
        }

        return challengeDtoList;
    }
}
