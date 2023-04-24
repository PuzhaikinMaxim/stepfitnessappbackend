package com.puj.stepfitnessapp.guildchallenges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrentGuildChallengeDto {

    private Integer pointsGained;

    private Integer goal;

    private String timeTillEnd;
}
