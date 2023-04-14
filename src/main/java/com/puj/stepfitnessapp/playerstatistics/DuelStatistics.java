package com.puj.stepfitnessapp.playerstatistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuelStatistics {

    private int amountOfDuelsWon;

    private int amountOfDuelsLost;

    private int rank;
}
