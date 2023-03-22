package com.puj.stepfitnessapp.player.inventory.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemDto {

    private int inventoryId;

    private int itemId;

    private String itemName;

    private int plusTimeMinutes = 0;

    private double timeMultiplier = 1.0;

    private int pointsFixed = 0;

    private double pointsMultiplier = 1.0;

    private int rarityLevel = 1;

    private boolean isEquipped = false;

    private Integer slot = null;

}
