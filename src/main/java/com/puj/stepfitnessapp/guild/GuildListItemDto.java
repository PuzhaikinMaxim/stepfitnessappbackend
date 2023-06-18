package com.puj.stepfitnessapp.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuildListItemDto {

    private Long guildId;

    private String guildName;

    private Integer rank;

    private Integer amountOfPlayers;

    private Boolean isEnterRequested;
}
