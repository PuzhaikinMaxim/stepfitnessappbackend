package com.puj.stepfitnessapp.userdailychallenge.dailychallenge;

import java.util.ArrayList;
import java.util.List;

public class DailyChallengeMapper {

    public DailyChallengeDto mapDailyChallengeToDailyChallengeDto(DailyChallenge dailyChallenge) {
        return new DailyChallengeDto(
                dailyChallenge.getAmountOfStepsToComplete(),
                dailyChallenge.isCompleted()
        );
    }

    public List<DailyChallengeDto> mapDailyChallengeListToDailyChallengeDtoList(
            List<DailyChallenge> dailyChallengeList
    ) {
        var list = new ArrayList<DailyChallengeDto>();
        for(var item : dailyChallengeList){
            list.add(mapDailyChallengeToDailyChallengeDto(item));
        }
        return list;
    }
}
