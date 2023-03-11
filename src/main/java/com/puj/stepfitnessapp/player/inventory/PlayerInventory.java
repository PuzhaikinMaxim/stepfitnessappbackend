package com.puj.stepfitnessapp.player.inventory;

import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerInventory {

    static final int FIRST_INVENTORY_SLOT = 1;

    InventoryItem[] equippedItems;

    ArrayList<InventoryItem> inventoryItems;

    public PlayerInventory() {
        equippedItems = new InventoryItem[2];
    }

    public void equipItem(int inventoryItemId, int slot){
        if(slot < 0 || slot >= equippedItems.length) return;
        equippedItems[slot - 1] = inventoryItems.get(inventoryItemId);
    }

    public void addItem(InventoryItem item) {
        item.setInventoryId(inventoryItems.size());
        inventoryItems.add(item);
    }

    public void addItems(List<InventoryItem> list) {
        inventoryItems.addAll(list);
    }
}
