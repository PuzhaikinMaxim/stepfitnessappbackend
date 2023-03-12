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

    public int calculateAmountOfMinutes(int amountOfMinutes) {
        var addedMinutes = 0;
        var minutesMultiplier = 0.0;
        for(InventoryItem equippedItem : equippedItems){
            addedMinutes += equippedItem.getPlusTimeMinutes();
            minutesMultiplier += equippedItem.getTimeMultiplier();
        }
        return (int) ((addedMinutes + addedMinutes)*minutesMultiplier);
    }

    public int calculateAmountOfPoints(int amountOfSteps) {
        var addedPoints = 0;
        var pointsMultiplier = 0.0;
        for (InventoryItem equippedItem : equippedItems) {
            addedPoints += (equippedItem.getPointsFixed() * amountOfSteps) / 100;
            pointsMultiplier += equippedItem.getPointsMultiplier();
        }
        return (int) ((amountOfSteps + addedPoints)*pointsMultiplier);
    }

    public void addItems(List<InventoryItem> list) {
        inventoryItems.addAll(list);
    }
}
