package com.puj.stepfitnessapp.player.inventory.item;

import com.puj.stepfitnessapp.items.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemMapper {

    private final static int DEFAULT_ID = 0;

    public InventoryItem mapItemToInventoryItem(Item item) {
        return new InventoryItem(
                DEFAULT_ID,
                item.getItemId(),
                item.getItemName(),
                item.getPlusTimeMinutes(),
                item.getTimeMultiplier(),
                item.getPointsFixed(),
                item.getPointsMultiplier(),
                item.getRarity()
        );
    }

    public List<InventoryItem> mapItemListToInventoryItemList(List<Item> list) {
        var newList = new ArrayList<InventoryItem>();
        for(Item item : list){
            newList.add(mapItemToInventoryItem(item));
        }
        return newList;
    }
}
