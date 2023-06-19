package com.puj.stepfitnessapp.items;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public List<ItemDto> itemListToItemDtoList(List<Item> items) {
        var itemDtos = new ArrayList<ItemDto>();
        for(Item item : items){
            itemDtos.add(itemToItemDto(item));
        }
        return itemDtos;
    }

    public ItemDto itemToItemDto(Item item) {
        return new ItemDto(
                item.getItemId(),
                item.getItemName(),
                item.getPlusTimeMinutes(),
                item.getTimeMultiplier(),
                item.getPointsFixed(),
                item.getPointsMultiplier(),
                item.getRarity().getRarityLevel(),
                item.getImageId()
        );
    }
}
