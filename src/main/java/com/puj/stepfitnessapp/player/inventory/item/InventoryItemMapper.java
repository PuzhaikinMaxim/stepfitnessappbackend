package com.puj.stepfitnessapp.player.inventory.item;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.item.dto.InventoryItemDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryItemMapper {

    private final static int DEFAULT_ID = 0;

    public InventoryItem mapItemToInventoryItem(Item item) {
        return new InventoryItem(
                DEFAULT_ID,
                item.getItemId()
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
            EquippedItem[] equippedItems,
            Map<Integer, Item> items
    ) {
        HashMap<Integer, Integer> equippedItemsMap = new HashMap<>();
        var counter = 0;
        for(EquippedItem item : equippedItems){
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
            var inventoryItemsDto
                    = mapInventoryItemToInventoryItemDto(
                            item,
                            isEquipped,
                            slot,
                            items.get(item.getItemId())
                    );
            inventoryItemDtos.add(inventoryItemsDto);
        }
        return inventoryItemDtos;
    }

    public InventoryItemDto mapInventoryItemToInventoryItemDto(
            InventoryItem inventoryItem,
            boolean isEquipped,
            Integer slot,
            Item item
    ) {
        return new InventoryItemDto(
                inventoryItem.getInventoryId(),
                inventoryItem.getItemId(),
                item.getItemName(),
                item.getPlusTimeMinutes(),
                item.getTimeMultiplier(),
                item.getPointsFixed(),
                item.getPointsMultiplier(),
                item.getRarity().getRarityLevel(),
                isEquipped,
                slot,
                item.getImageId()
        );
    }

    public EquippedItem mapToEquippedItem(Integer inventoryId, Item item) {
        return new EquippedItem(
                inventoryId,
                item.getItemId(),
                item.getPlusTimeMinutes(),
                item.getTimeMultiplier(),
                item.getPointsFixed(),
                item.getPointsMultiplier()
        );
    }
}
