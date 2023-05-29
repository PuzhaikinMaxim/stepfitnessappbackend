package com.puj.stepfitnessapp.playersrating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayersRatingDto {

    private String playerName;

    private Integer playerLevel;

    private Integer profileImageId;

    private Integer place;

    private Integer amountOfSteps = 0;

    private Integer amountOfDuelsWon = 0;
}
