package com.puj.stepfitnessapp.player.inventory.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemEquipDataDto {

    private int inventoryId;

    private int slot;
}
