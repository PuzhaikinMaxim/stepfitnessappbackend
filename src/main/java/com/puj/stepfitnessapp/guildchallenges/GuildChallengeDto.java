package com.puj.stepfitnessapp.guildchallenges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuildChallengeDto {

    private Long challengeId;

    private Integer goal;

    private Integer hoursToFinish;
}
