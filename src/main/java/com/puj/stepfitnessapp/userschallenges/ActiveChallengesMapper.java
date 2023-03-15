package com.puj.stepfitnessapp.userschallenges;

public class ActiveChallengesMapper {

    public UserChallengesDto mapUserChallengesToDto(UserChallenges userChallenges) {
        return new UserChallengesDto(
                userChallenges.getChallenge().getChallengeId(),
                userChallenges.getChallenge().getChallengeName(),
                userChallenges.getChallenge().getAmountOfPoints(),
                userChallenges.getChallengeEndDateTime().toString(),
                userChallenges.getProgress(),
                userChallenges.getAmountOfSteps(),
                userChallenges.getChallenge().getLevel().getChallengeLevel(),
                userChallenges.isCompleted(),
                userChallenges.isFailed()
        );
    }
}
