package com.puj.stepfitnessapp.player.inventory;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PlayerInventory {

    static final int FIRST_INVENTORY_SLOT = 1;

    InventoryItem[] equippedItems;

    ArrayList<InventoryItem> inventoryItems;

    public PlayerInventory() {
        equippedItems = new InventoryItem[2];
    }

    public void equipItem(InventoryItem inventoryItem, int slot){
        if(slot < 0 || slot >= equippedItems.length) return;
        equippedItems[slot - 1] = inventoryItem;
    }

    public void addItem(InventoryItem item){
        item.setInventoryId(inventoryItems.size());
        inventoryItems.add(item);
    }
}
