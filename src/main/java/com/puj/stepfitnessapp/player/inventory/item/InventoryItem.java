package com.puj.stepfitnessapp.player.inventory.item;

import com.puj.stepfitnessapp.rarity.Rarity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryItem {

    private int inventoryId;

    private int itemId;

    private String itemName;

    private int plusTimeMinutes = 0;

    private double timeMultiplier = 1.0;

    private int pointsFixed = 0;

    private double pointsMultiplier = 1.0;

    private Rarity rarityLevel;

}
