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

    InventoryItem[] equippedItems = new InventoryItem[2];

    ArrayList<InventoryItem> inventoryItems;

    public PlayerInventory() {

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
        var minutesMultiplier = 1.0;
        for(InventoryItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            addedMinutes += equippedItem.getPlusTimeMinutes();
            minutesMultiplier += equippedItem.getTimeMultiplier();
        }
        return (int) ((amountOfMinutes + addedMinutes)*minutesMultiplier);
    }

    public int calculateAmountOfPoints(int amountOfSteps) {
        var addedPoints = 0;
        var pointsMultiplier = 1.0;
        for (InventoryItem equippedItem : equippedItems) {
            if(equippedItem == null) break;
            addedPoints += (equippedItem.getPointsFixed() * amountOfSteps) / 100;
            pointsMultiplier += equippedItem.getPointsMultiplier();
        }
        return (int) ((amountOfSteps + addedPoints)*pointsMultiplier);
    }

    public void addItems(List<InventoryItem> list) {
        if(inventoryItems == null){
            this.inventoryItems = new ArrayList<>();
        }
        inventoryItems.addAll(list);
    }

    public void setEquippedItems(InventoryItem[] equippedItems){
        if(equippedItems == null){
            this.equippedItems = new InventoryItem[2];
        }
        this.equippedItems = equippedItems;
    }

    public void setInventoryItems(ArrayList<InventoryItem> inventoryItems){
        if(inventoryItems == null){
            this.inventoryItems = new ArrayList<>();
        }
        this.inventoryItems = inventoryItems;
    }
}
