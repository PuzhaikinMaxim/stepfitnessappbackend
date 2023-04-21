package com.puj.stepfitnessapp.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuildDto {

    private Long guildId;

    private Integer amountOfCompletedChallenges;

    private Integer xp;

    private String guildName;

}
