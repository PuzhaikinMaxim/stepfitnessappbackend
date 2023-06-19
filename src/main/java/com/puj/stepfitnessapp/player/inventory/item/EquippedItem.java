package com.puj.stepfitnessapp.player.inventory.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquippedItem {

    private int inventoryId;

    private int itemId;

    private int plusTimeMinutes = 0;

    private double timeMultiplier = 1.0;

    private int pointsFixed = 0;

    private double pointsMultiplier = 1.0;
}
