package com.puj.stepfitnessapp.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuildDataDto {

    private String guildName;

    private Integer guildRank;

    private Integer guildLogoId;
}
