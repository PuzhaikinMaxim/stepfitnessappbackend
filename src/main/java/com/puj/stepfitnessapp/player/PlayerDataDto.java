package com.puj.stepfitnessapp.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDataDto {

    private String username;

    private int profileImageId;

    private int level;

    private int amountOfXp;

    private int amountToGain;
}
