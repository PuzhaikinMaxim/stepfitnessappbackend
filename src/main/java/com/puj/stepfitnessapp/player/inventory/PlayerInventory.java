package com.puj.stepfitnessapp.player.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.item.EquippedItem;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItemMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerInventory {

    static final int FIRST_INVENTORY_SLOT = 1;

    EquippedItem[] equippedItems = new EquippedItem[2];

    ArrayList<InventoryItem> inventoryItems = new ArrayList<>();

    @JsonIgnore
    private final InventoryItemMapper mapper = new InventoryItemMapper();

    public PlayerInventory() {

    }

    public void equipItem(int inventoryItemId, int slot, Item item){
        if(slot < 0 || slot > equippedItems.length) return;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem != null && inventoryItemId == equippedItem.getInventoryId()){
                return;
            }
        }
        equippedItems[slot - 1] = mapper.mapToEquippedItem(
                inventoryItemId,
                item
        );
    }

    @JsonIgnore
    public InventoryItem getInventoryItem(int inventoryItemId) {
        for(InventoryItem item : inventoryItems){
            if(item.getInventoryId() == inventoryItemId){
                return item;
            }
        }
        return null;
    }

    public void addItem(InventoryItem item) {
        item.setInventoryId(inventoryItems.size());
        inventoryItems.add(item);
    }

    public void unEquipItem(int slot) {
        if(slot < 0 || slot > equippedItems.length) return;
        equippedItems[slot - 1] = null;
    }

    @JsonIgnore
    public int calculateAmountOfMinutes(int amountOfMinutes) {
        var addedMinutes = 0;
        var minutesMultiplier = 1.0;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            addedMinutes += equippedItem.getPlusTimeMinutes();
            minutesMultiplier += equippedItem.getTimeMultiplier();
        }
        return (int) ((amountOfMinutes + addedMinutes)*minutesMultiplier);
    }

    @JsonIgnore
    public int calculateAmountOfPoints(int amountOfSteps) {
        var addedPoints = 0;
        var pointsMultiplier = 1.0;
        for (EquippedItem equippedItem : equippedItems) {
            if(equippedItem == null) break;
            addedPoints += (equippedItem.getPointsFixed() * amountOfSteps) / 100;
            pointsMultiplier += equippedItem.getPointsMultiplier();
        }
        return (int) ((amountOfSteps + addedPoints)*pointsMultiplier);
    }

    @JsonIgnore
    public int calculateAmountOfAdditionalHp(int baseHp, int endurance) {
        var hpMultiplier = 1.0;
        var addedHp = 0;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            hpMultiplier += equippedItem.getTimeMultiplier();
            addedHp += equippedItem.getPlusTimeMinutes();
        }
        double additionalHp = ((baseHp + addedHp) * (hpMultiplier+(endurance*100.0/100.0))) - baseHp;
        return (int) additionalHp;
    }

    @JsonIgnore
    public Double calculatePointsMultiplier() {
        var pointsMultiplier = 1.0;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            pointsMultiplier += equippedItem.getPointsMultiplier();
        }
        return pointsMultiplier;
    }

    @JsonIgnore
    public int calculateAmountOfAdditionalPoints() {
        var additionalPoints = 0;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            additionalPoints += equippedItem.getPointsFixed();
        }
        return additionalPoints;
    }

    @JsonIgnore
    public Double calculateTimeMultiplier() {
        var timeMultiplier = 1.0;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            timeMultiplier += equippedItem.getTimeMultiplier();
        }
        return timeMultiplier;
    }

    @JsonIgnore
    public Integer calculateAdditionalMinutes() {
        var additionalMinutes = 0;
        for(EquippedItem equippedItem : equippedItems){
            if(equippedItem == null) break;
            additionalMinutes += equippedItem.getPlusTimeMinutes();
        }
        return additionalMinutes;
    }

    public void addItems(List<InventoryItem> list) {
        if(inventoryItems == null){
            this.inventoryItems = new ArrayList<>();
        }
        for(InventoryItem item : list){
            addItem(item);
        }
    }

    @JsonIgnore
    public List<Integer> getItemIds() {
        var itemIds = new ArrayList<Integer>();
        for(InventoryItem item : inventoryItems){
            itemIds.add(item.getItemId());
        }
        return itemIds;
    }

    public void setEquippedItems(EquippedItem[] equippedItems){
        if(equippedItems == null){
            this.equippedItems = new EquippedItem[2];
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
