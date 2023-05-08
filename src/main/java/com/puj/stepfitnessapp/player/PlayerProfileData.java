package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.guild.GuildDataDto;
import com.puj.stepfitnessapp.guild.GuildDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerProfileData {

    private PlayerDataDto userData;

    private int amountOfCompletedChallenges;

    private int amountOfAchievements;

    private int amountOfSteps;

    private int duelsWon;

    private String guildName;

    private Integer guildLogoId;
}
