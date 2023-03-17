package com.puj.stepfitnessapp.challengelevel;

import com.puj.stepfitnessapp.challenge.LevelChallenges;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatistics;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallengeLevelMapper {

    public List<ChallengeLevelDto> mapPlayerStatisticsAndChallengeLevelListToChallengeLevelDto(
            PlayerStatistics playerStatistics,
            List<ChallengeLevel> challengeLevelList,
            List<LevelChallenges> levelChallenges
    ) {
        final var playerChallengesMap = getCompletedChallengesMapFromList(
                playerStatistics.getCompletedChallenges()
        );
        final var levelChallengesMap = getLevelChallengesMapFromList(
                levelChallenges
        );
        ArrayList<ChallengeLevelDto> challengeLevelDtoList = new ArrayList<>();
        for(ChallengeLevel challengeLevel : challengeLevelList){
            var completedChallenges = playerChallengesMap.get(challengeLevel.getChallengeLevel());
            var levelChallenge = levelChallengesMap.get(challengeLevel.getChallengeLevel());
            var challengeLevelDto = new ChallengeLevelDto();

            challengeLevelDto.setDungeonLevel(challengeLevel.getChallengeLevel());
            challengeLevelDto.setMinimalLevelRequirements(challengeLevel.getMinimumUserLevel());
            setAmountOfCompletedChallenges(challengeLevelDto, completedChallenges);
            setAmountOfChallenges(challengeLevelDto, levelChallenge);
            setMinimumUserLevel(challengeLevelDto, challengeLevel, playerStatistics);
        }
        return challengeLevelDtoList;
    }

    private void setAmountOfCompletedChallenges(ChallengeLevelDto elem, CompletedChallenges completedChallenges) {
        if(completedChallenges != null){
            elem.setAmountOfCompletedChallenges(completedChallenges.getAmountOfCompletedChallenges());
        }
    }

    private void setAmountOfChallenges(ChallengeLevelDto elem, LevelChallenges levelChallenges) {
        if(levelChallenges != null){
            elem.setAmountOfChallenges(levelChallenges.getChallengeCount());
        }
    }

    private void setMinimumUserLevel(
            ChallengeLevelDto elem,
            ChallengeLevel challengeLevel,
            PlayerStatistics playerStatistics
    ) {
        if(playerStatistics.getPlayer().getLevel() >= challengeLevel.getMinimumUserLevel()){
            elem.setLocked(false);
        }
    }

    private Map<Integer, CompletedChallenges> getCompletedChallengesMapFromList(List<CompletedChallenges> list) {
        HashMap<Integer, CompletedChallenges> map = new HashMap<>();

        for(CompletedChallenges item : list){
            map.put(item.getLevel(), item);
        }
        return map;
    }

    private Map<Integer, LevelChallenges> getLevelChallengesMapFromList(List<LevelChallenges> list) {
        HashMap<Integer, LevelChallenges> map = new HashMap<>();

        for(LevelChallenges item : list){
            map.put(item.getLevel(), item);
        }
        return map;
    }
}
