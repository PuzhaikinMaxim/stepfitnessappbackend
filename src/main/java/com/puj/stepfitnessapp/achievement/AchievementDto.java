package com.puj.stepfitnessapp.achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {

    private int achievementId;

    private String achievementName;

    private String achievementDescription;

    private int achievementType;

    private boolean isCompleted;
}
