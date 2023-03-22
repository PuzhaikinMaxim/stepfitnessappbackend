package com.puj.stepfitnessapp.player.inventory.item;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.item.dto.InventoryItemDto;

import java.util.ArrayList;
import java.util.HashMap;
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

    public List<InventoryItemDto> mapInventoryItemListToInventoryItemDtoList(
            List<InventoryItem> inventoryItems,
            InventoryItem[] equippedItems
    ) {
        HashMap<Integer, Integer> equippedItemsMap = new HashMap<>();
        var counter = 0;
        for(InventoryItem item : equippedItems){
            if(item != null){
                equippedItemsMap.put(item.getInventoryId(), counter);
            }
            counter++;
        }

        ArrayList<InventoryItemDto> inventoryItemDtos = new ArrayList<>();
        for(InventoryItem item : inventoryItems){
            var isEquipped = equippedItemsMap.containsKey(item.getInventoryId());
            Integer slot = null;
            if(isEquipped){
                slot = equippedItemsMap.get(item.getInventoryId());
            }
            var inventoryItemsDto = mapInventoryItemToInventoryItemDto(item, isEquipped, slot);
            inventoryItemDtos.add(inventoryItemsDto);
        }
        return inventoryItemDtos;
    }

    public InventoryItemDto mapInventoryItemToInventoryItemDto(InventoryItem item, boolean isEquipped, Integer slot) {
        return new InventoryItemDto(
                item.getInventoryId(),
                item.getItemId(),
                item.getItemName(),
                item.getPlusTimeMinutes(),
                item.getTimeMultiplier(),
                item.getPointsFixed(),
                item.getPointsMultiplier(),
                item.getRarityLevel().getRarityLevel(),
                isEquipped,
                slot
        );
    }
}
