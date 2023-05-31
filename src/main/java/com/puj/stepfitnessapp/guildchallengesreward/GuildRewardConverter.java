package com.puj.stepfitnessapp.guildchallengesreward;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;

import javax.persistence.AttributeConverter;
import java.util.List;

public class GuildRewardConverter implements AttributeConverter<List<InventoryItem>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<InventoryItem> fromJsonToItemsList(String json){
        try {
            return objectMapper.readValue(json,  new TypeReference<>() {});
        }
        catch (JsonProcessingException ex) {
            return null;
        }
    }

    public String fromItemsListToJson(List<InventoryItem> itemsList){
        try {
            return objectMapper.writeValueAsString(itemsList);
        }
        catch (JsonProcessingException ex) {
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(List<InventoryItem> attribute) {
        return fromItemsListToJson(attribute);
    }

    @Override
    public List<InventoryItem> convertToEntityAttribute(String dbData) {
        return fromJsonToItemsList(dbData);
    }
}
