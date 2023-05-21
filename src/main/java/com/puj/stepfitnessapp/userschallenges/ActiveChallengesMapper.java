package com.puj.stepfitnessapp.userschallenges;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ActiveChallengesMapper {

    public UserChallengesDto mapUserChallengesToDto(UserChallenges userChallenges) {
        final var challengeEndDateTime = userChallenges.getChallengeEndDateTime();

        String timeTillEndText = "";

        long minutesDiff = ChronoUnit.MINUTES.between(
                LocalDateTime.now(), challengeEndDateTime
        );

        if(challengeEndDateTime.compareTo(LocalDateTime.now()) < 0){
            timeTillEndText = "Время окончено";
        }
        else {
            if(minutesDiff / 60 > 0){
                timeTillEndText = (minutesDiff/60) + "ч" + (minutesDiff%60) + "м";
            }
            else{
                timeTillEndText = minutesDiff + "м";
            }
        }
        if(userChallenges.isCompleted()){
            timeTillEndText = "Испытание выполнено";
        }

        return new UserChallengesDto(
                userChallenges.getChallenge().getChallengeId(),
                userChallenges.getChallenge().getChallengeName(),
                userChallenges.getChallenge().getAmountOfPoints(),
                timeTillEndText,
                userChallenges.getProgress(),
                userChallenges.getAmountOfSteps(),
                userChallenges.getChallenge().getLevel().getChallengeLevel(),
                userChallenges.isCompleted(),
                userChallenges.isFailed()
        );
    }
}
