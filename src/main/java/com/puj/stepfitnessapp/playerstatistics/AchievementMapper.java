package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.achievement.Achievement;
import com.puj.stepfitnessapp.achievement.AchievementDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AchievementMapper {

    public List<AchievementDto> mapAchievementListAndCompletedAchievementsToAchievementDtoList(
            List<Achievement> achievementList,
            Set<Integer> completedAchievementList
    ) {
        var achievementDtoList = new ArrayList<AchievementDto>();
        for(Achievement achievement : achievementList){
            var achievementDto = mapAchievementToAchievementDto(
                    achievement,
                    completedAchievementList.contains(achievement.getAchievementId())
            );
            achievementDtoList.add(achievementDto);
        }
        return achievementDtoList;
    }

    public AchievementDto mapAchievementToAchievementDto(Achievement achievement, boolean isCompleted) {
        return new AchievementDto(
                achievement.getAchievementId(),
                achievement.getAchievementName(),
                achievement.getAchievementCategory().getDescription(),
                achievement.getAchievementCategory().getType(),
                isCompleted
        );
    }
}
